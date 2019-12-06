<?php
require_once '../include/head_php.inc';

$pageTitle = 'บทสนทนา (Dialogues)';
$pageTitleShort = 'บทสนทนา';
$placeType = '';
$userHasPermission = true;

$sql = "SELECT *
            FROM efm_sentence
            ORDER BY id";
if ($result = $db->query($sql)) {
    $placeList = array();
    while ($row = $result->fetch_assoc()) {
        array_push($placeList, $row);
    }
    $result->close();
} else {
    echo 'เกิดข้อผิดพลาดในการเชื่อมต่อฐานข้อมูล';
    $db->close();
    exit();
}

?>
    <!DOCTYPE html>
    <html lang="th">
    <head>
        <?php require_once('../include/head.inc'); ?>
        <!-- DataTables -->
        <link rel="stylesheet" href="../bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
        <!--Lightbox-->
        <link href="../dist/lightbox/css/lightbox.css" rel="stylesheet">

        <style>
            #tableDownload td:nth-child(5) {
                text-align: center;
            }
        </style>
    </head>
    <body class="hold-transition skin-purple sidebar-mini fixed">

    <div class="wrapper">
        <?php require_once('../include/header.inc'); ?>
        <?php require_once('../include/sidebar.inc'); ?>

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    <?= trim($pageTitle); ?>
                </h1>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box">
                            <div class="box-header">
                                <h3 class="box-title">&nbsp;</h3>
                                <?php
                                if ($userHasPermission) {
                                    ?>
                                    <button type="button" class="btn btn-success pull-right"
                                            onclick="onClickAdd(this)">
                                        <span class="fa fa-plus"></span>&nbsp;
                                        เพิ่ม<?= $pageTitleShort; ?>
                                    </button>
                                    <?php
                                }
                                ?>
                            </div>
                            <div class="box-body">
                                <table id="tablePlace_<?= $placeType; ?>" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th style="text-align: center; width: 40%;">แม่</th>
                                        <!--<th style="text-align: center;">เสียง</th>-->
                                        <th style="text-align: center; width: 40%;">ลูก</th>
                                        <!--<th style="text-align: center;">เสียง</th>-->
                                        <th style="text-align: center; width: 20%;">หมวดหมู่</th>
                                        <th style="text-align: center;">จัดการ</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <?php
                                    if (sizeof($placeList) == 0) {
                                        ?>
                                        <!--<tr valign="middle">
                                            <td colspan="20" align="center">ไม่มีข้อมูล</td>
                                        </tr>-->
                                        <?php
                                    } else {
                                        foreach ($placeList as $place) {
                                            ?>
                                            <tr style="">
                                                <!--แม่-->
                                                <td>
                                                    <?= "<div style=\"margin-bottom: 5px\"><strong>{$place['mom_english']}</strong></div><div style=\"margin-bottom: 5px\">{$place['mom_thai']}</div>"; ?>
                                                    <?php
                                                    if ($place['mom_sound_file']) {
                                                        ?>
                                                        <audio controls>
                                                            <source src="../audio/<?= $place['mom_sound_file']; ?>" type="audio/mpeg">
                                                            บราวเซอร์ของคุณไม่รองรับการเล่นไฟล์เสียง
                                                        </audio>
                                                        <?php
                                                    } else {
                                                        ?>
                                                        <span class="label label-danger" style="padding: 5px">ไม่มีข้อมูลไฟล์เสียง</span>
                                                        <?php
                                                    }
                                                    ?>
                                                </td>
                                                <!--เสียงแม่-->
                                                <!--<td style="text-align: center"><?/*= $place['mom_sound_file']; */ ?></td>-->
                                                <!--ลูก-->
                                                <td>
                                                    <?= "<div style=\"margin-bottom: 5px\"><strong>{$place['child_english']}</strong></div><div style=\"margin-bottom: 5px\">{$place['child_thai']}</div>"; ?>
                                                    <?php
                                                    if ($place['child_sound_file']) {
                                                        ?>
                                                        <audio controls>
                                                            <source src="../audio/<?= $place['child_sound_file']; ?>" type="audio/mpeg">
                                                            บราวเซอร์ของคุณไม่รองรับการเล่นไฟล์เสียง
                                                        </audio>
                                                        <?php
                                                    } else {
                                                        ?>
                                                        <span class="label label-danger" style="padding: 5px">ไม่มีข้อมูลไฟล์เสียง</span>
                                                        <?php
                                                    }
                                                    ?>
                                                </td>
                                                <!--เสียงลูก-->
                                                <!--<td style="text-align: center"><?/*= $place['child_sound_file']; */ ?></td>-->
                                                <!--หมวดหมู่-->
                                                <?php
                                                $categoryText = null;
                                                switch ($place['category']) {
                                                    case 'morning':
                                                        $categoryText = 'Early Morning Routine';
                                                        break;
                                                    case 'school':
                                                        $categoryText = 'Before and After School';
                                                        break;
                                                    case 'playground':
                                                        $categoryText = 'Fun at the Playground';
                                                        break;
                                                    case 'eat':
                                                        $categoryText = 'A Happy Eating Time';
                                                        break;
                                                    case 'weekend':
                                                        $categoryText = 'Family Trip on Weekend';
                                                        break;
                                                }
                                                ?>
                                                <td style="text-align: center"><?= $categoryText; ?></td>

                                                <td nowrap>
                                                    <form method="get" action="sentence_add_edit.php" style="display: inline; margin: 0">
                                                        <input type="hidden" name="place_id" value="<?= $place['id']; ?>"/>

                                                        <?php
                                                        if ($userHasPermission) {
                                                            ?>
                                                            <button type="submit" class="btn btn-warning"
                                                                    style="margin-left: 3px">
                                                                <span class="fa fa-pencil"></span>&nbsp;
                                                                แก้ไข
                                                            </button>
                                                            <button type="button" class="btn btn-danger"
                                                                    style="margin-left: 3px; margin-right: 3px"
                                                                    onclick="onClickDelete(this, <?= $place['id']; ?>)">
                                                                <span class="fa fa-remove"></span>&nbsp;
                                                                ลบ
                                                            </button>
                                                            <?php
                                                        }
                                                        ?>
                                                    </form>
                                                </td>
                                            </tr>
                                            <?php
                                        }
                                    }
                                    ?>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <?php require_once('../include/footer.inc'); ?>
    </div>
    <!-- ./wrapper -->

    <script>
        let downloadListDataTable = null;

        $(document).ready(function () {
            $('#tablePlace_<?= $placeType; ?>').DataTable({
                stateSave: true,
                stateDuration: -1, // sessionStorage
                order: [[3, 'desc']],
                language: {
                    lengthMenu: "แสดงหน้าละ _MENU_ แถวข้อมูล",
                    zeroRecords: "ไม่มีข้อมูล",
                    emptyTable: "ไม่มีข้อมูล",
                    info: "หน้าที่ _PAGE_ จากทั้งหมด _PAGES_ หน้า",
                    infoEmpty: "แสดง 0 แถวข้อมูล",
                    infoFiltered: "(กรองจากทั้งหมด _MAX_ แถวข้อมูล)",
                    search: "ค้นหา:",
                    thousands: ",",
                    loadingRecords: "รอสักครู่...",
                    processing: "กำลังประมวลผล...",
                    paginate: {
                        first: "หน้าแรก",
                        last: "หน้าสุดท้าย",
                        next: "ถัดไป",
                        previous: "ก่อนหน้า"
                    },
                },
                drawCallback: function (row, data) {
                    $('.my-toggle').bootstrapToggle();
                }
            });

            lightbox.option({
                fadeDuration: 500,
                imageFadeDuration: 500,
                resizeDuration: 500,
            });
        });

        function onClickAdd() {
            window.location.href = 'sentence_add_edit.php';
        }

        function onChangePlaceRecommend(element, placeId) {
            let result = confirm("ยืนยัน" + (element.checked ? 'ตั้ง' : 'ยกเลิก') + "เป็น<?= $placeType === 'otop' ? 'สินค้า' : 'สถานที่' ?>แนะนำ?");
            ;
            if (result) {
                doChangePlaceRecommend(placeId, (element.checked ? 1 : 0));
            } else {
                /*รีโหลด เพื่อให้สถานะ checkbox กลับมาเหมือนเดิม*/
                location.reload(true);
            }
        }

        function doChangePlaceRecommend(placeId, recommend) {
            let title = 'แก้ไขสถานะ <?= $placeType === 'otop' ? 'สินค้า' : 'สถานที่' ?>แนะนำ';

            $.post(
                '../api/api.php/update_place_recommend',
                {
                    placeId: placeId,
                    recommend: recommend,
                }
            ).done(function (data) {
                if (data.error_code === 0) {
                    location.reload(true);
                } else {
                    BootstrapDialog.show({
                        title: title + ' - ผิดพลาด',
                        message: data.error_message,
                        buttons: [{
                            label: 'ปิด',
                            action: function (self) {
                                self.close();
                                location.reload(true);
                            }
                        }]
                    });
                }
            }).fail(function () {
                BootstrapDialog.show({
                    title: title + ' - ผิดพลาด',
                    message: 'เกิดข้อผิดพลาดในการเชื่อมต่อ Server',
                    buttons: [{
                        label: 'ปิด',
                        action: function (self) {
                            self.close();
                            location.reload(true);
                        }
                    }]
                });
            });
        }

        function onClickDelete(element, id) {
            BootstrapDialog.show({
                title: 'ลบ<?= $pageTitle; ?>',
                message: 'ยืนยันลบรายการนี้?',
                buttons: [{
                    label: 'ลบ',
                    action: function (self) {
                        doDeleteplace(id);
                        self.close();
                    },
                    cssClass: 'btn-primary'
                }, {
                    label: 'ยกเลิก',
                    action: function (self) {
                        self.close();
                    }
                }]
            });
        }

        function doDeleteplace(id) {
            $.post(
                '../api/api.php/delete_sentence',
                {
                    id: id,
                }
            ).done(function (data) {
                if (data.error_code === 0) {
                    location.reload(true);
                } else {
                    BootstrapDialog.show({
                        title: 'ลบ<?= $pageTitle; ?> - ผิดพลาด',
                        message: data.error_message,
                        buttons: [{
                            label: 'ปิด',
                            action: function (self) {
                                self.close();
                            }
                        }]
                    });
                }
            }).fail(function () {
                BootstrapDialog.show({
                    title: 'ลบ<?= $pageTitle; ?> - ผิดพลาด',
                    message: 'เกิดข้อผิดพลาดในการเชื่อมต่อ Server',
                    buttons: [{
                        label: 'ปิด',
                        action: function (self) {
                            self.close();
                        }
                    }]
                });
            });
        }
    </script>

    <?php require_once('../include/foot.inc'); ?>
    <!-- DataTables -->
    <script src="../bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <!--Lightbox-->
    <script src="../dist/lightbox/js/lightbox.js"></script>

    </body>
    </html>

<?php
require_once '../include/foot_php.inc';
?>