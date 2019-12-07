<?php
session_start();
require_once 'global.php';

error_reporting(E_ERROR | E_PARSE);
header('Content-type: application/json; charset=utf-8');

header('Expires: Sun, 01 Jan 2014 00:00:00 GMT');
header('Cache-Control: no-store, no-cache, must-revalidate');
header('Cache-Control: post-check=0, pre-check=0', FALSE);
header('Pragma: no-cache');

$response = array();

$request = explode('/', trim($_SERVER['PATH_INFO'], '/'));
$action = strtolower(array_shift($request));
$id = array_shift($request);

require_once 'db_config.php';
$db = new mysqli(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

if ($db->connect_errno) {
    $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
    $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการเชื่อมต่อฐานข้อมูล';
    $response[KEY_ERROR_MESSAGE_MORE] = $db->connect_error;
    echo json_encode($response);
    exit();
}
$db->set_charset("utf8");

//sleep(1); //todo:

switch ($action) {
    case 'get_sentence':
        doGetSentence();
        break;
    case 'add_sentence':
        doAddSentence();
        break;
    case 'update_sentence':
        doUpdateSentence();
        break;
    case 'delete_sentence':
        doDeleteSentence();
        break;
    case 'delete_project_asset':
        doDeleteProjectAsset();
        break;
    default:
        $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
        $response[KEY_ERROR_MESSAGE] = "No action specified or invalid action: [{$action}]";
        $response[KEY_ERROR_MESSAGE_MORE] = '';
        break;
}

$db->close();
echo json_encode($response);
exit();

function doGetSentence()
{
    global $db, $response;

    $where = ' TRUE ';
    if (isset($_GET['category'])) {
        $category = $db->real_escape_string($_GET['category']);
        $where .= " AND category = '{$category}' ";
    }

    $sql = "SELECT * FROM efm_sentence 
            WHERE $where 
            ORDER BY id";
    if ($result = $db->query($sql)) {
        $response[KEY_ERROR_CODE] = ERROR_CODE_SUCCESS;
        $response[KEY_ERROR_MESSAGE] = 'อ่านข้อมูลสำเร็จ';
        $response[KEY_ERROR_MESSAGE_MORE] = '';

        $placeList = array();
        while ($row = $result->fetch_assoc()) {
            $project = array();
            $project['id'] = (int)$row['id'];
            $project['mom_english'] = $row['mom_english'];
            $project['mom_thai'] = $row['mom_thai'];
            $project['child_english'] = $row['child_english'];
            $project['child_thai'] = $row['child_thai'];
            $project['mom_sound_file'] = $row['mom_sound_file'];
            $project['child_sound_file'] = $row['child_sound_file'];
            $project['category'] = $row['category'];

            array_push($placeList, $project);
        }
        $result->close();
        $response[KEY_DATA_LIST] = $placeList;
    } else {
        $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
        $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการอ่านข้อมูล';
        $errMessage = $db->error;
        $response[KEY_ERROR_MESSAGE_MORE] = "$errMessage\nSQL: $sql";
    }
}

function doAddSentence()
{
    global $db, $response;

    $momEnglish = trim($db->real_escape_string($_POST['momEnglish']));
    $momThai = trim($db->real_escape_string($_POST['momThai']));
    $childEnglish = trim($db->real_escape_string($_POST['childEnglish']));
    $childThai = trim($db->real_escape_string($_POST['childThai']));
    $category = $db->real_escape_string($_POST['category']);

    if (!moveUploadedFile('momSoundFile', DIR_AUDIO, $momSoundFileName)) {
        $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
        $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการอัพโหลดไฟล์ (1)';
        $response[KEY_ERROR_MESSAGE_MORE] = '';
        return;
    }
    if (!moveUploadedFile('childSoundFile', DIR_AUDIO, $childSoundFileName)) {
        $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
        $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการอัพโหลดไฟล์ (2)';
        $response[KEY_ERROR_MESSAGE_MORE] = '';
        return;
    }

    $sql = "INSERT INTO efm_sentence (mom_english, mom_thai, child_english, child_thai,  
                        mom_sound_file, child_sound_file, category) 
                VALUES ('$momEnglish', '$momThai', '$childEnglish', '$childThai', 
                        '$momSoundFileName', '$childSoundFileName', '$category')";

    if ($result = $db->query($sql)) {
        $response[KEY_ERROR_CODE] = ERROR_CODE_SUCCESS;
        $response[KEY_ERROR_MESSAGE] = 'เพิ่มข้อมูลสำเร็จ';
        $response[KEY_ERROR_MESSAGE_MORE] = '';
    } else {
        $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
        $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการเพิ่มข้อมูล: ' . $db->error;
        $errMessage = $db->error;
        $response[KEY_ERROR_MESSAGE_MORE] = "$errMessage\nSQL: $sql";
    }
}

function doUpdateSentence()
{
    global $db, $response;

    $id = $db->real_escape_string($_POST['placeId']);
    $momEnglish = trim($db->real_escape_string($_POST['momEnglish']));
    $momThai = trim($db->real_escape_string($_POST['momThai']));
    $childEnglish = trim($db->real_escape_string($_POST['childEnglish']));
    $childThai = trim($db->real_escape_string($_POST['childThai']));
    $category = $db->real_escape_string($_POST['category']);

    $momSoundFileName = NULL;
    if ($_FILES['momSoundFile']['name'] !== '') {
        if (!moveUploadedFile('momSoundFile', DIR_AUDIO, $momSoundFileName)) {
            $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
            $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการอัพโหลดไฟล์ (1)';
            $response[KEY_ERROR_MESSAGE_MORE] = '';
            return;
        }
    }
    $setFileName = $momSoundFileName ? "mom_sound_file = '$momSoundFileName', " : '';

    $childSoundFileName = NULL;
    if ($_FILES['childSoundFile']['name'] !== '') {
        if (!moveUploadedFile('childSoundFile', DIR_AUDIO, $childSoundFileName)) {
            $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
            $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการอัพโหลดไฟล์ (2)';
            $response[KEY_ERROR_MESSAGE_MORE] = '';
            return;
        }
    }
    $setFileName .= $childSoundFileName ? "child_sound_file = '$childSoundFileName', " : '';

    $sql = "UPDATE efm_sentence 
                SET $setFileName 
                    mom_english = '$momEnglish', mom_thai = '$momThai', 
                    child_english = '$childEnglish', child_thai = '$childThai', 
                    category = '$category' 
                WHERE id = $id";

    if ($result = $db->query($sql)) {
        $response[KEY_ERROR_CODE] = ERROR_CODE_SUCCESS;
        $response[KEY_ERROR_MESSAGE] = 'แก้ไขข้อมูลสำเร็จ';
        $response[KEY_ERROR_MESSAGE_MORE] = '';
    } else {
        $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
        $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการแก้ไขข้อมูล: ' . $db->error;
        $errMessage = $db->error;
        $response[KEY_ERROR_MESSAGE_MORE] = "$errMessage\nSQL: $sql";
    }
}

function doDeleteSentence()
{
    global $db, $response;

    $id = $db->real_escape_string($_POST['id']);

    $sql = "DELETE FROM efm_sentence WHERE id = $id";

    if ($deleteResult = $db->query($sql)) {
        $response[KEY_ERROR_CODE] = ERROR_CODE_SUCCESS;
        $response[KEY_ERROR_MESSAGE] = 'ลบข้อมูลสำเร็จ';
        $response[KEY_ERROR_MESSAGE_MORE] = '';
    } else {
        $response[KEY_ERROR_CODE] = ERROR_CODE_ERROR;
        $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการลบข้อมูล: ' . $db->error;
        $errMessage = $db->error;
        $response[KEY_ERROR_MESSAGE_MORE] = "$errMessage\nSQL: $sql";
    }
}

function doDeleteProjectAsset()
{
    global $db, $response;

    $assetId = $db->real_escape_string($_POST['assetId']);

    $sql = "DELETE FROM rp_project_image WHERE id = $assetId";
    if ($result = $db->query($sql)) {
        $response[KEY_ERROR_CODE] = ERROR_CODE_SUCCESS;
        $response[KEY_ERROR_MESSAGE] = 'ลบข้อมูลสำเร็จ';
        $response[KEY_ERROR_MESSAGE_MORE] = '';
    } else {
        $response[KEY_ERROR_CODE] = ERROR_CODE_SQL_ERROR;
        $response[KEY_ERROR_MESSAGE] = 'เกิดข้อผิดพลาดในการลบข้อมูล';
        $errMessage = $db->error;
        $response[KEY_ERROR_MESSAGE_MORE] = "$errMessage\nSQL: $sql";
    }
}

function createRandomString($length)
{
    $key = '';
    $keys = array_merge(range(0, 9), range('a', 'z'));

    for ($i = 0; $i < $length; $i++) {
        $key .= $keys[array_rand($keys)];
    }

    return $key;
}

function moveUploadedFile($key, $dest, &$randomFileName, $index = -1)
{
    global $response;

    $clientName = $index === -1 ? $_FILES[$key]['name'] : $_FILES[$key]['name'][$index];
    $response['name'] = $clientName;
    $response['type'] = $index === -1 ? $_FILES[$key]['type'] : $_FILES[$key]['type'][$index];
    $response['size'] = $index === -1 ? $_FILES[$key]['size'] : $_FILES[$key]['size'][$index];
    $response['tmp_name'] = $index === -1 ? $_FILES[$key]['tmp_name'] : $_FILES[$key]['tmp_name'][$index];

    $src = $index === -1 ? $_FILES[$key]['tmp_name'] : $_FILES[$key]['tmp_name'][$index];
    $response['upload_src'] = $src;
    $response['upload_dest'] = $dest;

    //$date = date('Y-m-d H:i:s');
    //$timestamp = time();
    $timestamp = round(microtime(true) * 1000);
    $randomFileName = "{$timestamp}-{$clientName}";
    return move_uploaded_file($src, "{$dest}{$randomFileName}");
}

function moveUploadedFile_Old($key, $dest)
{
    global $response;

    $response['name'] = $_FILES[$key]['name'];
    $response['type'] = $_FILES[$key]['type'];
    $response['size'] = $_FILES[$key]['size'];
    $response['tmp_name'] = $_FILES[$key]['tmp_name'];

    $src = $_FILES[$key]['tmp_name'];
    $response['upload_src'] = $src;

    $response['upload_dest'] = $dest;

    return move_uploaded_file($src, $dest);
}

function getUploadErrorMessage($errCode)
{
    $message = '';
    switch ($errCode) {
        case UPLOAD_ERR_OK:
            break;
        case UPLOAD_ERR_INI_SIZE:
        case UPLOAD_ERR_FORM_SIZE:
            $message .= 'File too large (limit of ' . get_max_upload() . ' bytes).';
            break;
        case UPLOAD_ERR_PARTIAL:
            $message .= 'File upload was not completed.';
            break;
        case UPLOAD_ERR_NO_FILE:
            $message .= 'Zero-length file uploaded.';
            break;
        default:
            $message .= 'Internal error #' . $errCode;
            break;
    }
    return $message;
}

?>
