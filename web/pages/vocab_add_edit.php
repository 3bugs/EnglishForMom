<?php
require_once '../include/head_php.inc';

$placeId = $_GET['place_id'];

$pageTitle = 'คำศัพท์';

$place = array();
if (isset($placeId)) {
    $placeId = $db->real_escape_string($placeId);

    $sql = "SELECT * 
            FROM efm_vocab 
            WHERE id = $placeId";

    if ($result = $db->query($sql)) {
        if ($result->num_rows > 0) {
            $place = $result->fetch_assoc();
        } else {
            echo 'ไม่พบข้อมูล';
            $result->close();
            $db->close();
            exit();
        }
        $result->close();
    } else {
        echo 'เกิดข้อผิดพลาดในการเชื่อมต่อฐานข้อมูล: ' . $db->error;
        $db->close();
        exit();
    }
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
            input[type="file"] {
                margin-bottom: 15px;
                /*display: none;*/
            }

            .preview-container {
                position: relative;
                display: inline;
                border: 0px solid red;
            }

            .preview-container .selFile {
                opacity: 1;
                transition: .3s ease;
            }

            .preview-container:hover .selFile {
                opacity: 0.3;
                transition: .3s ease;
            }

            .middle {
                transition: .3s ease;
                opacity: 0;
                position: absolute;
                top: 50%;
                left: 80px;
                transform: translate(-50%, -50%);
                -ms-transform: translate(-50%, -50%);
                text-align: center;
            }

            .middle:hover {
                cursor: pointer;
            }

            .preview-container:hover .middle {
                opacity: 1;
                transition: .3s ease;
            }

            .custom-file-upload {
                border: 1px solid #ccc;
                display: inline-block;
                padding: 6px 12px;
                cursor: pointer;
            }

            .custom-file-upload:hover {
                background: #f4f4f4;
            }

            .nav-tabs {
                background-color: #f8f8f8;
            }

            .tab-content {
                /*background-color:#ccc;
                color:#00ff00;
                padding:5px*/
            }

            .nav-tabs > li > a {
                /*border: medium none;*/
            }

            .nav-tabs > li > a:hover {
                /*background-color: #ccc !important;
                border: medium none;
                border-radius: 0;
                color:#fff;*/
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
                    <?= (isset($placeId) ? 'แก้ไข' : 'เพิ่ม') . $pageTitle; ?>
                </h1>
            </section>

            <!-- Main content -->
            <section class="content">
                <form id="formAddPlace"
                      autocomplete="off"
                      action="../api/api.php/<?= (isset($placeId) ? 'update_project' : 'add_project'); ?>"
                      method="post">

                    <input type="hidden" name="placeId" value="<?php echo $placeId; ?>"/>

                    <div class="row">
                        <div class="col-xs-12">

                            <!--หมวดหมู่-->
                            <div class="box box-success">
                                <div class="box-header with-border">
                                    <h3 class="box-title">หมวดหมู่</h3>

                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"
                                                data-toggle="tooltip" title="ย่อ">
                                            <i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                    <!-- /.box-tools -->
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">

                                    <div class="row">
                                        <!--หมวดหมู่-->
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <!--<label for="selectCategory">หมวดหมู่:</label>-->
                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-folder-open"></i>
                                                </span>
                                                    <select id="selectCategory" class="form-control" required
                                                            name="category"
                                                            oninvalid="this.setCustomValidity('เลือกหมวดหมู่')"
                                                            oninput="this.setCustomValidity('')">
                                                        <option value="" disabled <?= empty($place) ? 'selected' : ''; ?>>-- เลือกหมวดหมู่ --</option>
                                                        <?php
                                                        $categoryValList = array(
                                                            'morning', 'school', 'playground', 'eat', 'weekend'
                                                        );
                                                        $categoryTextList['morning'] = 'Early Morning Routine';
                                                        $categoryTextList['school'] = 'Before and After School';
                                                        $categoryTextList['playground'] = 'Fun at the Playground';
                                                        $categoryTextList['eat'] = 'A Happy Eating Time';
                                                        $categoryTextList['weekend'] = 'Family Trip on Weekend';

                                                        foreach ($categoryValList as $categoryVal) {
                                                            $categoryText = $categoryTextList[$categoryVal];
                                                            ?>
                                                            <option value="<?= $categoryVal; ?>" <?= (!empty($place) && ($place['category'] === $categoryVal)) ? 'selected' : ''; ?>>
                                                                <?= $categoryText; ?>
                                                            </option>
                                                            <?php
                                                        }
                                                        ?>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <!--ประโยคแม่-->
                            <div class="box box-warning">
                                <div class="box-header with-border">
                                    <h3 class="box-title">คำศัพท์</h3>

                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"
                                                data-toggle="tooltip" title="ย่อ">
                                            <i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                    <!-- /.box-tools -->
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">

                                    <!--คำศัพท์-->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label for="inputWord">คำศัพท์:</label>
                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-font"></i>
                                                </span>
                                                    <input type="text" class="form-control"
                                                           id="inputWord"
                                                           name="word"
                                                           value="<?php echo(!empty($place) ? $place['word'] : ''); ?>"
                                                           placeholder="กรอกคำศัพท์" required
                                                           oninvalid="this.setCustomValidity('กรอกคำศัพท์')"
                                                           oninput="this.setCustomValidity('')"
                                                           style="font-family: monospace">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!--ความหมาย-->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label for="inputMeaning">ความหมาย:</label>
                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <!--<i class="fa fa-font"></i>-->
                                                    <strong>ก</strong>
                                                </span>
                                                    <input type="text" class="form-control"
                                                           id="inputMeaning"
                                                           name="meaning"
                                                           value="<?php echo(!empty($place) ? $place['meaning'] : ''); ?>"
                                                           placeholder="กรอกความหมาย" required
                                                           oninvalid="this.setCustomValidity('กรอกความหมาย')"
                                                           oninput="this.setCustomValidity('')">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!--หน้าที่ของคำ-->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label for="selectPartOfSpeech">หน้าที่ของคำ:</label>
                                                <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-tag"></i>
                                                </span>
                                                    <select id="selectPartOfSpeech" class="form-control" required
                                                            name="partOfSpeech"
                                                            oninvalid="this.setCustomValidity('เลือกหน้าที่ของคำ')"
                                                            oninput="this.setCustomValidity('')">
                                                        <option value="" disabled <?= empty($place) ? 'selected' : ''; ?>>-- เลือกหน้าที่ของคำ --</option>
                                                        <?php
                                                        $partOfSpeechValList = array(
                                                            'noun', 'pronoun', 'verb', 'adjective', 'adverb', 'preposition', 'conjunction', 'interjection'
                                                        );
                                                        $partOfSpeechTextList['noun'] = 'นาม (Noun)';
                                                        $partOfSpeechTextList['pronoun'] = 'สรรพนาม (Pronoun)';
                                                        $partOfSpeechTextList['verb'] = 'กริยา (Verb)';
                                                        $partOfSpeechTextList['adjective'] = 'คุณศัพท์ (Adjective)';
                                                        $partOfSpeechTextList['adverb'] = 'กริยาวิเศษณ์ (Adverb)';
                                                        $partOfSpeechTextList['preposition'] = 'บุพบท (Preposition)';
                                                        $partOfSpeechTextList['conjunction'] = 'สันธาน (Conjunction)';
                                                        $partOfSpeechTextList['interjection'] = 'คำอุทาน (Interjection)';

                                                        foreach ($partOfSpeechValList as $partOfSpeechVal) {
                                                            $partOfSpeechText = $partOfSpeechTextList[$partOfSpeechVal];
                                                            ?>
                                                            <option value="<?= $partOfSpeechVal; ?>" <?= (!empty($place) && ($place['part_of_speech'] === $partOfSpeechVal)) ? 'selected' : ''; ?>>
                                                                <?= $partOfSpeechText; ?>
                                                            </option>
                                                            <?php
                                                        }
                                                        ?>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->

                            <!--ปุ่ม "บันทึก"-->
                            <div class="row">
                                <div class="col-12 text-center">
                                    <div id="divLoading" style="text-align: center; margin-bottom: 10px;">
                                        <img src="../images/ic_loading4.gif" height="32px"/>&nbsp;รอสักครู่
                                    </div>
                                    <button id="buttonSave" type="submit"
                                            class="btn btn-info">
                                        <span class="fa fa-save"></span>&nbsp;
                                        บันทึก
                                    </button>
                                    <!--<button id="buttonSave" type="button"
                                            class="btn btn-danger">
                                        <span class="fa fa-ban"></span>&nbsp;
                                        ยังไม่พร้อมใช้งานครับ
                                    </button>-->
                                </div>
                            </div>

                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </form>

            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <?php require_once('../include/footer.inc'); ?>
    </div>
    <!-- ./wrapper -->

    <script>
        //https://www.raymondcamden.com/2014/04/14/MultiFile-Uploads-and-Multiple-Selects-Part-2/

        /*function test() {
            $('#image-upload').prop('files').splice(0, 1);
            alert($('#image-upload').prop('files').length());
        }*/

        $(document).ready(function () {
            $('#formAddPlace #divLoading').hide();

            $('#formAddPlace').submit(event => {
                event.preventDefault();
                doAddEditPlace();
            });
        });

        $(function () {
            $('#momSoundFilePreview').hide();
            $('#childSoundFilePreview').hide();

            const previewFile = function (input, placeToInsertPreview) {
                $(placeToInsertPreview).empty();
                $(placeToInsertPreview).hide();

                if (input.files) {
                    let fileCount = input.files.length;

                    for (let i = 0; i < fileCount; i++) {
                        $(placeToInsertPreview).show();
                        let reader = new FileReader();

                        reader.onload = function (event) {
                            //$($.parseHTML('<img style="width: auto; height: 120px; margin: 3px">'))
                            const audioElement = $($.parseHTML('<audio controls></audio>'))
                                .appendTo(placeToInsertPreview);
                            $($.parseHTML('<source type="audio/mpeg">'))
                                .attr('src', event.target.result)
                                .appendTo(audioElement);
                        };
                        reader.readAsDataURL(input.files[i]);
                    }
                }
            };

            $('#inputMomSoundFile').on('change', function () {
                previewFile(this, 'div#momSoundFilePreview');
            });
            $('#inputChildSoundFile').on('change', function () {
                previewFile(this, 'div#childSoundFilePreview');
            });
        });

        function doAddEditPlace() {
            // อัพเดท content ของ ckeditor ไปยัง textarea
            //CKEDITOR.instances.editor.updateElement();

            $('#formAddPlace #buttonSave').prop('disabled', true);
            $('#formAddPlace #divLoading').show();

            const form = $('#formAddPlace')[0];
            const formData = new FormData(form);

            $.ajax({
                url: '../api/api.php/<?= (isset($placeId) ? 'update_vocab' : 'add_vocab'); ?>',
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                method: 'POST',
                type: 'POST',
                success: function (data) {
                    $('#formAddPlace #buttonSave').prop('disabled', false);
                    $('#formAddPlace #divLoading').hide();

                    if (data.error_code === 0) {
                        BootstrapDialog.show({
                            title: '<?php echo(isset($placeId) ? 'แก้ไข' : 'เพิ่ม'); ?>',
                            message: data.error_message,
                            buttons: [{
                                label: 'ปิด',
                                action: function (self) {
                                    self.close();
                                    <?php
                                    if (!isset($placeId)) {
                                    ?>
                                    window.location.href = 'vocab.php';
                                    <?php
                                    } else {
                                    ?>
                                    window.location.reload(true);
                                    <?php
                                    }
                                    ?>
                                }
                            }]
                        });
                    } else {
                        BootstrapDialog.show({
                            title: '<?php echo(isset($placeId) ? 'แก้ไข' : 'เพิ่ม'); ?> - ผิดพลาด',
                            message: data.error_message,
                            buttons: [{
                                label: 'ปิด',
                                action: function (self) {
                                    self.close();
                                }
                            }]
                        });
                    }
                },
                error: function () {
                    $('#formAddPlace #buttonSave').prop('disabled', false);
                    $('#formAddPlace #divLoading').hide();

                    BootstrapDialog.show({
                        title: '<?php echo(isset($placeId) ? 'แก้ไข' : 'เพิ่ม'); ?> - ผิดพลาด',
                        message: 'เกิดข้อผิดพลาดในการเชื่อมต่อ Server',
                        buttons: [{
                            label: 'ปิด',
                            action: function (self) {
                                self.close();
                            }
                        }]
                    });
                }
            });

            $('#formAddPlace_NotUsed').ajaxSubmit({
                dataType: 'json',
                success: (data, statusText) => {
                    //alert(data.error_message);
                    $('#formAddPlace #buttonSave').prop('disabled', false);
                    $('#formAddPlace #divLoading').hide();

                    if (data.error_code === 0) {
                        BootstrapDialog.show({
                            title: '<?php echo(isset($placeId) ? 'แก้ไข' : 'เพิ่ม'); ?>',
                            message: data.error_message,
                            buttons: [{
                                label: 'ปิด',
                                action: function (self) {
                                    self.close();
                                    <?php
                                    if (!isset($placeId)) {
                                    ?>
                                    window.location.href = 'place.php';
                                    <?php
                                    } else {
                                    ?>
                                    window.location.reload(true);
                                    <?php
                                    }
                                    ?>
                                }
                            }]
                        });
                    } else {
                        BootstrapDialog.show({
                            title: '<?php echo(isset($placeId) ? 'แก้ไข' : 'เพิ่ม'); ?> - ผิดพลาด',
                            message: data.error_message,
                            buttons: [{
                                label: 'ปิด',
                                action: function (self) {
                                    self.close();
                                }
                            }]
                        });
                    }
                },
                error: () => {
                    $('#formAddPlace #buttonSave').prop('disabled', false);
                    $('#formAddPlace #divLoading').hide();

                    BootstrapDialog.show({
                        title: '<?php echo(isset($placeId) ? 'แก้ไข' : 'เพิ่ม'); ?> - ผิดพลาด',
                        message: 'เกิดข้อผิดพลาดในการเชื่อมต่อ Server',
                        buttons: [{
                            label: 'ปิด',
                            action: function (self) {
                                self.close();
                            }
                        }]
                    });
                }
            });
        }

        function onClickDeleteAsset(element, assetId, assetType) {
            BootstrapDialog.show({
                title: 'ลบ' + assetType,
                message: `การลบ${assetType}จะมีผลกับฐานข้อมูลทันที!\n\nยืนยันลบ${assetType}นี้?`,
                buttons: [{
                    label: 'ลบ',
                    action: function (self) {
                        doDeleteAsset(assetId, assetType);
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

        function doDeleteAsset(assetId, assetType) {
            $.post(
                '../api/api.php/delete_project_asset',
                {
                    assetId: assetId,
                }
            ).done(function (data) {
                if (data.error_code === 0) {
                    location.reload(true);
                } else {
                    BootstrapDialog.show({
                        title: 'ลบ' + assetType + ' - ผิดพลาด',
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
                    title: 'ลบ' + assetType + ' - ผิดพลาด',
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

    <?php
    if ($placeType === 'otop') {
        ?>
        <script>
            const vmDistrict = new Vue({
                el: '#selectDistrict',
                data: {
                    //selectedDistrict: null,
                },
                methods: {
                    async handleSelectDistrict(e) {
                        const result = await getSubDistrict(e.target.value);
                        vmSubDistrict.subDistrictList = result.data_list;
                        vmSubDistrict.selectedSubDistrict = '';
                    }
                },
                created: function () {
                }
            });

            const vmSubDistrict = new Vue({
                el: '#selectSubDistrict',
                data: {
                    subDistrictList: [],
                    selectedSubDistrict: '',
                },
                methods: {
                    handleSelectSubDistrict(e) {
                    }
                },
                created: async function () {
                    <?php
                    if (!empty($place)) {
                    ?>
                    const result = await getSubDistrict('<?= $place['district']; ?>');
                    this.subDistrictList = result.data_list;
                    this.selectedSubDistrict = '<?= $place['sub_district']; ?>';
                    <?php
                    }
                    ?>
                }
            });

            /*const vmVillage = new Vue({
                el: '#selectVillage',
                data: {
                    villageList: [
                    ],
                    selectedVillage: '',
                },
                methods: {
                    handleSelectVillage(e) {
                        //alert(e.target.value);
                    }
                }
            });*/

            async function getSubDistrict(district) {
                const response = await fetch('http://5911011802058.msci.dusit.ac.th/chainat_tourism/api/api.php/get_sub_district?district=' + district, {
                    method: 'GET', // *GET, POST, PUT, DELETE, etc.
                    mode: 'cors', // no-cors, *cors, same-origin
                    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                    credentials: 'same-origin', // include, *same-origin, omit
                    headers: {
                        //'Content-Type': 'application/json'
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    redirect: 'follow', // manual, *follow, error
                    referrer: 'no-referrer', // no-referrer, *client
                    //body: JSON.stringify(data) // body data type must match "Content-Type" header
                });
                return await response.json(); // parses JSON response into native JavaScript objects
            }
        </script>
        <?php
    }
    ?>

    <?php require_once('../include/foot.inc'); ?>
    <!-- CK Editor -->
    <!--<script src="../bower_components/ckeditor/ckeditor.js"></script>-->
    <!-- DataTables -->
    <script src="../bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <!--jQuery Form Plugin-->
    <script src="../dist/js/jquery.form.js"></script>
    <!--Lightbox-->
    <script src="../dist/lightbox/js/lightbox.js"></script>

    </body>
    </html>

<?php
require_once '../include/foot_php.inc';
?>