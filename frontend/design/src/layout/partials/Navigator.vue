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
                <li class="nav-item">
                    <router-link class="nav-link" to="/">
                    <i class="ti-home menu-icon menu-icon"></i>
                    <span class="menu-title">Dashboard</span>
                    </router-link>
                </li>
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