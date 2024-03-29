<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu" data-widget="tree" style="margin-top: 10px">
            <li>
                <a href="sentence.php">
                    <i class="fa fa-commenting-o"></i> <span>บทสนทนา (Dialogues)</span>
                </a>
            </li>
            <li>
                <a href="vocab.php">
                    <i class="fa fa-font"></i> <span>คำศัพท์ (Vocabulary)</span>
                </a>
            </li>
            <li>
                <a href="animation.php">
                    <i class="fa fa-play-circle"></i> <span>Animation</span>
                </a>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>
<script>
    $(document).ready(function () {
        $('#logoutMenu').click(function (e) {
            BootstrapDialog.show({
                title: 'Confirm Logout',
                message: 'ยืนยันออกจากระบบ?',
                buttons: [
                    {
                        label: 'ยกเลิก',
                        action: function (self) {
                            self.close();
                        }
                    },
                    {
                        label: 'ออกจากระบบ',
                        action: function (self) {
                            self.close();
                            doLogout();
                        }
                    }
                ]
            });
        });

        // Sets active link in Bootstrap menu
        // Add this code in a central place used\shared by all pages
        // like your _Layout.cshtml in ASP.NET MVC for example
        const url = (location.pathname + location.search).substr(1);
        $('a[href="' + url + '"]').parents('li,ul').addClass('active');
        //alert(url);
    });
</script>