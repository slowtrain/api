   <template>
    <nav class="bottom-navbar">
        <div class="container">
            <ul class="nav page-navigation">
                <!--
                <li class="nav-item">
                    <router-link class="nav-link" to="/">
                    <i class="ti-home menu-icon menu-icon"></i>
                    <span class="menu-title">Dashboard</span>
                    </router-link>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0);" class="nav-link">
                    <i class="ti-settings menu-icon"></i>
                    <span class="menu-title">Open API Platform</span>
                    <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                    <ul class="submenu-item">
                        <li class="nav-item">
                            <router-link class="nav-link" to="/info/platform">Open API Platform? </router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/info/use">이용 절차</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/info/register">가입 절차</router-link>
                        </li>
                    </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/">
                    <i class="ti-home menu-icon menu-icon"></i>
                    <span class="menu-title">API & Service</span>
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/">
                    <i class="ti-home menu-icon menu-icon"></i>
                    <span class="menu-title">제휴 스토리</span>
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" to="/">
                    <i class="ti-home menu-icon menu-icon"></i>
                    <span class="menu-title">테스트베드</span>
                    </router-link>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0);" class="nav-link">
                    <i class="ti-settings menu-icon"></i>
                    <span class="menu-title">커뮤니티</span>
                    <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/">포럼</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/">FAQ</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/">서비스 Q&A</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/">기술 Q&A</router-link>
                        </li>
                    </ul>
                    </div>
                </li>
                -->
                <li class="nav-item">
                    <a href="javascript:void(0);" class="nav-link">
                        <i class="ti-settings menu-icon"></i>
                        <span class="menu-title">Design</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                    <ul class="submenu-item">
                        <li class="nav-item">
                            <router-link class="nav-link" to="/design/testform">Testform</router-link>
                        </li>
                    </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0);" class="nav-link">
                        <i class="ti-settings menu-icon"></i>
                        <span class="menu-title">Open API Platform</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                    <ul class="submenu-item">
                        <li class="nav-item">
                            <router-link class="nav-link" to="/info/platform">Open API Platform? </router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/info/use">이용 절차</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link class="nav-link" to="/info/register">가입 절차</router-link>
                        </li>
                    </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a href="javascript:void(0);" class="nav-link">
                    <i class="ti-settings menu-icon"></i>
                    <span class="menu-title">통합관제</span>
                    <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/">대시보드</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/">E2E</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/">이벤트</router-link>
                        </li>
                    </ul>
                    </div>
                </li>
                <li class="nav-item mega-menu">
                    <a href="javascript:void(0);" class="nav-link">
                    <i class="ti-settings menu-icon"></i>
                    <span class="menu-title">IT Infra</span>
                    <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                        <div class="col-group-wrapper row">
                            <div class="col-group col-md-3">
                                <p class="category-heading">서비스 관리</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/">대시보드</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">서비스</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/usergroup">손실비용</router-link>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-group col-md-3">
                                <p class="category-heading">라이선스 관리</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/workflow/designer">라이선스</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/workflow/action">라이선스 사용</router-link>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-group col-md-3">
                                <p class="category-heading">IT 자원</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/application">CI 변경이력</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/organization">CI</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/domain">Discoveried CI</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/navigator">소프트웨어</router-link>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-group col-md-3">
                                <p class="category-heading">관리</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/application">소프트웨어 카달로그</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/organization">변환</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/domain">업체관리</router-link>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="nav-item mega-menu">
                    <a href="javascript:void(0);" class="nav-link">
                    <i class="ti-settings menu-icon"></i>
                    <span class="menu-title">System</span>
                    <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                        <div class="col-group-wrapper row">
                            <div class="col-group col-md-2">
                                <p class="category-heading">Gateway</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/application">Gateway</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">SR</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">API</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">APPS</router-link>
                                    </li>
                                </ul>
                            </div>
                      
                            <div class="col-group col-md-2">
                                <p class="category-heading">Policy</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">과금 정책</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">할인 정책</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">청구 내역</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">기관할인 정책</router-link>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-group col-md-2">
                                <p class="category-heading">Statistics</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">API 사용 현황</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">API 제공 현황</router-link>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-group col-md-2">
                                <p class="category-heading">Content</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">이용약관</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">개인정보 처리방침</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">개인정보 수집동의서</router-link>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-group col-md-2">
                                <p class="category-heading">System</p>
                                <ul class="submenu-item">
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/user">User</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/usergroup">Usergroup</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/user/role">Role</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/workflow/designer">Designer</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/workflow/action">Action</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/workflow/assignment">Assignment</router-link>
                                    </li>
                                </ul>
                            </div>
                            <div class="col-group col-md-2">
                                <p class="category-heading">Config</p>
                                <ul class="submenu-item">

                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/application">Application</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/organization">Organization</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/domain">Domain</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/navigator">Navigator</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/scheduler">Scheduler</router-link>
                                    </li>
                                    <li class="nav-item">
                                        <router-link class="nav-link" to="/system/config/classification">Classification</router-link>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </li>
                <!--
                <li class="nav-item">
                    <a href="javascript:void(0);" class="nav-link">
                    <i class="ti-settings menu-icon"></i>
                    <span class="menu-title">System</span>
                    <i class="menu-arrow"></i>
                    </a>
                    <div class="submenu">
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/system/user">사용자</router-link>
                        </li>
                    </ul>
                    </div>
                </li>
                -->
            </ul>
        </div>
        </nav>
</template>
<script>
    export default {
        name: "navigator",
        data () {
            return {

            }
        },
        computed: {
        }
    }
</script>