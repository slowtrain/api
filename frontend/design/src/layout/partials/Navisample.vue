   <template>
    <nav class="bottom-navbar">
        <div class="container-fluid">
            <ul class="nav page-navigation">
            <li class="nav-item">
                <router-link class="nav-link" to="/">
                <i class="ti-home menu-icon menu-icon"></i>
                <span class="menu-title">Dashboard</span>
                </router-link>
            </li>
            <li class="nav-item mega-menu">
                <a href="javascript:void(0);" class="nav-link">
                <i class="ti-palette menu-icon"></i>
                <span class="menu-title">UI Elements</span>
                <i class="menu-arrow"></i>
                </a>
                <div class="submenu">
                <div class="col-group-wrapper row">
                    <div class="col-group col-md-4">
                    <div class="row">
                        <div class="col-12">
                        <p class="category-heading">Basic Elements</p>
                        <div class="submenu-item">
                            <div class="row">
                            <div class="col-md-6">
                                <ul>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/accordions/">Accordion</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/alerts/">Alerts</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/badges/">Badges</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link
                                    class="nav-link"
                                    to="/basic-ui/breadcrumbs/"
                                    >Breadcrumbs</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/buttons/">Buttons</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/dropdowns/">Dropdowns</router-link>
                                </li>
                                </ul>
                            </div>
                            <div class="col-md-6">
                                <ul>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/modals/">Modals</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link
                                    class="nav-link"
                                    to="/basic-ui/paginations/"
                                    >Paginations</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/progress/">Progress</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/basic-ui/tabs/">Tabs</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link
                                    class="nav-link"
                                    to="/basic-ui/tooltips/"
                                    >Tooltips & Popovers</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link
                                    class="nav-link"
                                    to="/basic-ui/typography/"
                                    >Typography</router-link>
                                </li>
                                </ul>
                            </div>
                            </div>
                        </div>
                        </div>
                    </div>
                    </div>
                    <div class="col-group col-md-4">
                    <div class="row">
                        <div class="col-12">
                        <p class="category-heading">Advanced Elements</p>
                        <div class="submenu-item">
                            <div class="row">
                            <div class="col-md-6">
                                <ul>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/advanced-ui/dragula/">Dragula</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/advanced-ui/carousel/">Carousel</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/advanced-ui/loaders/">Loaders</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link class="nav-link" to="/advanced-ui/slider/">Slider</router-link>
                                </li>
                                </ul>
                            </div>
                            <div class="col-md-6">
                                <ul>
                                <li class="nav-item">
                                    <router-link
                                    class="nav-link"
                                    to="/advanced-ui/clipboard/"
                                    >Clipboard</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link
                                    class="nav-link"
                                    to="/advanced-ui/tree-view/"
                                    >Tree View</router-link>
                                </li>
                                <li class="nav-item">
                                    <router-link
                                    class="nav-link"
                                    to="/advanced-ui/context-menu/"
                                    >Context Menu</router-link>
                                </li>
                                </ul>
                            </div>
                            </div>
                        </div>
                        </div>
                    </div>
                    </div>
                    <div class="col-group col-md-4">
                    <p class="category-heading">Icons</p>
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/icons/flag-icons/">Flag icons</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/icons/fontawesome/">Font Awesome</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/icons/mdi-icons/">MDI</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/icons/simpleline/">Simple line icons</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/icons/themify-icons/">Themify icons</router-link>
                        </li>
                    </ul>
                    </div>
                </div>
                </div>
            </li>
            <li class="nav-item">
                <a href="javascript:void(0);" class="nav-link">
                <i class="ti-clipboard menu-icon"></i>
                <span class="menu-title">Forms</span>
                <i class="menu-arrow"></i>
                </a>
                <div class="submenu">
                <ul class="submenu-item">
                    <li class="nav-item">
                    <router-link class="nav-link" to="/forms/basic-elements/">Basic Elements</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/forms/advanced-elements/">Advanced Elements</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/forms/wizard/">Wizard</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/forms/validation/">Validation</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/text-editors/">Text Editors</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/code-editors/">Code Editors</router-link>
                    </li>
                </ul>
                </div>
            </li>
            <li class="nav-item mega-menu">
                <a href="javascript:void(0);" class="nav-link">
                <i class="ti-bar-chart menu-icon"></i>
                <span class="menu-title">Data</span>
                <i class="menu-arrow"></i>
                </a>
                <div class="submenu">
                <div class="col-group-wrapper row">
                    <div class="col-group col-md-6">
                    <p class="category-heading">Charts</p>
                    <div class="submenu-item">
                        <div class="row">
                        <div class="col-md-6">
                            <ul>
                            <li class="nav-item">
                                <router-link class="nav-link" to="/charts/chartjs/">Chart js</router-link>
                            </li>
                            <li class="nav-item">
                                <router-link class="nav-link" to="/charts/c3/">C3 charts</router-link>
                            </li>
                            <li class="nav-item">
                                <router-link class="nav-link" to="/charts/chartist/">Chartist</router-link>
                            </li>
                            </ul>
                        </div>
                        <div class="col-md-6">
                            <ul>
                            <li class="nav-item">
                                <router-link class="nav-link" to="/charts/googleChart/">Google Chart</router-link>
                            </li>
                            <li class="nav-item">
                                <router-link class="nav-link" to="/charts/justgage/">Justgage</router-link>
                            </li>
                            </ul>
                        </div>
                        </div>
                    </div>
                    </div>
                    <div class="col-group col-md-3">
                    <p class="category-heading">Table</p>
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/tables/basic-tables/">Basic Table</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/tables/advanced-tables/">Advanced Table</router-link>
                        </li>
                    </ul>
                    </div>
                    <div class="col-group col-md-3">
                    <p class="category-heading">Maps</p>
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/maps/google-map/">Google Maps</router-link>
                        </li>
                    </ul>
                    </div>
                </div>
                </div>
            </li>
            <li class="nav-item mega-menu">
                <a href="javascript:void(0);" class="nav-link">
                <i class="ti-files menu-icon"></i>
                <span class="menu-title">Sample Pages</span>
                <i class="menu-arrow"></i>
                </a>
                <div class="submenu">
                <div class="col-group-wrapper row">
                    <div class="col-group col-md-2">
                    <p class="category-heading">User Pages</p>
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/auth-pages/login/">Login</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/auth-pages/login-2/">Login - 2</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link
                            class="nav-link"
                            to="/auth-pages/multi-level-login/"
                        >Multi Level Login</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/auth-pages/register/">Register</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/auth-pages/register-2/">Register - 2</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/auth-pages/lock-screen/">Lockscreen</router-link>
                        </li>
                    </ul>
                    </div>
                    <div class="col-group col-md-2">
                    <p class="category-heading">Error Pages</p>
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/error-pages/error-404/">404</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/error-pages/error-500/">500</router-link>
                        </li>
                    </ul>
                    </div>
                    <div class="col-group col-md-3">
                    <p class="category-heading">E-commerce</p>
                    <ul class="submenu-item">
                        <li class="nav-item">
                        <router-link class="nav-link" to="/e-commerce/invoice/">Invoice</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/e-commerce/orders/">Orders</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/e-commerce/pricing-table/">Pricing Table</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link
                            class="nav-link"
                            to="/e-commerce/product-catalogue/"
                        >Product Catalogue</router-link>
                        </li>
                        <li class="nav-item">
                        <router-link class="nav-link" to="/e-commerce/project-list/">Project list</router-link>
                        </li>
                    </ul>
                    </div>
                    <div class="col-group col-md-5">
                    <p class="category-heading">General</p>
                    <div class="row">
                        <div class="col-md-6">
                        <ul class="submenu-item">
                            <li class="nav-item">
                            <router-link class="nav-link" to="/general-pages/blank-page/">Blank Page</router-link>
                            </li>
                            <li class="nav-item">
                            <router-link class="nav-link" to="/general-pages/profile/">Profile</router-link>
                            </li>
                            <li class="nav-item">
                            <router-link class="nav-link" to="/general-pages/faq-1/">FAQ</router-link>
                            </li>
                            <li class="nav-item">
                            <router-link class="nav-link" to="/general-pages/faq-2/">FAQ 2</router-link>
                            </li>
                            <li class="nav-item">
                            <router-link class="nav-link" to="/general-pages/news-grid/">News Grid</router-link>
                            </li>
                        </ul>
                        </div>
                        <div class="col-md-6">
                        <ul>
                            <li class="nav-item">
                            <router-link class="nav-link" to="/general-pages/timeline/">Timeline</router-link>
                            </li>
                            <li class="nav-item">
                            <router-link
                                class="nav-link"
                                to="/general-pages/search-result/"
                            >Search Result</router-link>
                            </li>
                            <li class="nav-item">
                            <router-link class="nav-link" to="/general-pages/portfolio/">Portfolio</router-link>
                            </li>
                            <li class="nav-item">
                            <router-link
                                class="nav-link"
                                to="/general-pages/user-listing/"
                            >User Listing</router-link>
                            </li>
                        </ul>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </li>
            <li class="nav-item">
                <a href="javascript:void(0);" class="nav-link">
                <i class="ti-package menu-icon"></i>
                <span class="menu-title">Apps</span>
                <i class="menu-arrow"></i>
                </a>
                <div class="submenu">
                <ul class="submenu-item">
                    <li class="nav-item">
                    <router-link class="nav-link" to="/apps/calendar/">Calendar</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/apps/email/">E-mail</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/apps/todo/">To-do</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/apps/gallery/">Gallery</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/apps/tickets/">Tickets</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/apps/kanban/">Kanban Board</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/apps/chat/">Chat</router-link>
                    </li>
                    <li class="nav-item">
                    <router-link class="nav-link" to="/widgets/">Widgets</router-link>
                    </li>
                </ul>
                </div>
            </li>
            <li class="nav-item">
                <router-link class="nav-link" to="/popups/">
                <i class="ti-comment-alt menu-icon"></i>
                <span class="menu-title">Popups</span>
                </router-link>
            </li>
            <li class="nav-item">
                <router-link class="nav-link" to="/notifications/">
                <i class="ti-bell menu-icon"></i>
                <span class="menu-title">Notifications</span>
                </router-link>
            </li>
            <li class="nav-item">
                <a
                href="http://www.urbanui.com/justdo-vue-pro/documentation/documentation.html"
                class="nav-link"
                target="_blank"
                >
                <i class="ti-file menu-icon"></i>
                <span class="menu-title">Docs</span>
                </a>
            </li>
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