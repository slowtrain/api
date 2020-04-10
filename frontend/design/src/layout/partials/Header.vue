<template>
  <b-navbar id="template-header" class="navbar default-layout-navbar p-0 fixed-top" toggleable="lg">
    <div class="horizontal-menu">
      <nav class="navbar top-navbar col-lg-12 col-12 p-0">
        <div class="container-fluid" >
          <div
            class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center"
          >
            <router-link class="navbar-brand brand-logo" to="/">
              <!-- <img src="@/assets/images/logo-white.svg" alt="logo" /> -->
              <img src="@/assets/images/episode.svg" alt="logo"  /> 
            </router-link>
            <router-link class="navbar-brand brand-logo-mini" to="/">
              <!-- <img src="@/assets/images/logo-mini.svg" alt="logo" /> -->
              <img src="@/assets/images/episode.svg" alt="logo" />
            </router-link>
          </div>
          <div class="navbar-menu-wrapper d-flex align-items-center ml-auto ml-lg-0">
            <button
              class="navbar-toggler navbar-toggler align-self-center d-lg-block"
              type="button"
              @click="toggleSidebar()"
            >
            <span class="ti-layout-grid2"></span>
            </button>
            <!--
            <div class="nav-search d-none d-lg-block">
              <form action="#">
                <div class="input-group">
                  <div class="input-group-prepend hover-cursor" id="navbar-search-icon">
                    <span class="input-group-text" id="search">
                      <i class="ti-search"></i>
                    </span>
                  </div>
                  <input
                    type="text"
                    class="form-control"
                    id="navbar-search-input"
                    placeholder="Search now"
                    aria-label="search"
                    aria-describedby="search"
                  />
                </div>
              </form>
            </div>
            -->
            <b-navbar-nav class="navbar-nav-right ml-auto">
              <!--
              <b-nav-item-dropdown right class="preview-list mr-1">
                <template slot="button-content">
                  <div class="nav-link count-indicator dropdown-toggle">
                    <i class="ti-email mx-0"></i>
                  </div>
                </template>
                <h6 class="p-3 mb-0">Messages</h6>
                <b-dropdown-item class="preview-item">
                  <div class="preview-thumbnail">
                    <img src="https://via.placeholder.com/36X36" alt="image" class="profile-pic" />
                  </div>
                  <div class="preview-item-content flex-grow">
                    <h6 class="preview-subject ellipsis font-weight-normal">David Grey</h6>
                    <p class="font-weight-light small-text text-muted mb-0">The meeting is cancelled</p>
                  </div>
                </b-dropdown-item>
                <b-dropdown-item class="preview-item">
                  <div class="preview-thumbnail">
                    <img src="https://via.placeholder.com/36X36" alt="image" class="profile-pic" />
                  </div>
                  <div class="preview-item-content flex-grow">
                    <h6 class="preview-subject ellipsis font-weight-normal">Tim Cook</h6>
                    <p class="font-weight-light small-text text-muted mb-0">New product launch</p>
                  </div>
                </b-dropdown-item>
                <b-dropdown-item class="preview-item">
                  <div class="preview-thumbnail">
                    <img src="https://via.placeholder.com/36X36" alt="image" class="profile-pic" />
                  </div>
                  <div class="preview-item-content flex-grow">
                    <h6 class="preview-subject ellipsis font-weight-normal">Johnson</h6>
                    <p class="font-weight-light small-text text-muted mb-0">Upcoming board meeting</p>
                  </div>
                </b-dropdown-item>
              </b-nav-item-dropdown>
              <b-nav-item-dropdown right class="preview-list">
                <template slot="button-content">
                  <div class="nav-link count-indicator dropdown-toggle">
                    <i class="ti-bell mx-0"></i>
                    <span class="count"></span>
                  </div>
                </template>
                <h6 class="p-3 mb-0">Notifications</h6>
                <b-dropdown-item class="preview-item">
                  <div class="preview-thumbnail">
                    <div class="preview-icon bg-success">
                      <i class="ti-info-alt mx-0"></i>
                    </div>
                  </div>
                  <div class="preview-item-content">
                    <h6 class="preview-subject font-weight-normal">Application Error</h6>
                    <p class="font-weight-light small-text mb-0 text-muted">Just now</p>
                  </div>
                </b-dropdown-item>
                <b-dropdown-item class="preview-item">
                  <div class="preview-thumbnail">
                    <div class="preview-icon bg-warning">
                      <i class="ti-settings mx-0"></i>
                    </div>
                  </div>
                  <div class="preview-item-content">
                    <h6 class="preview-subject font-weight-normal">Settings</h6>
                    <p class="font-weight-light small-text mb-0 text-muted">Private message</p>
                  </div>
                </b-dropdown-item>
                <b-dropdown-item class="preview-item">
                  <div class="preview-thumbnail">
                    <div class="preview-icon bg-info">
                      <i class="ti-user mx-0"></i>
                    </div>
                  </div>
                  <div class="preview-item-content">
                    <h6 class="preview-subject font-weight-normal">New user registration</h6>
                    <p class="font-weight-light small-text mb-0 text-muted">2 days ago</p>
                  </div>
                </b-dropdown-item>
              </b-nav-item-dropdown>
              -->
              <b-nav-item-dropdown right class="nav-profile">
                <template slot="button-content" >
                  <span class="nav-link dropdown-toggle" id="profileDropdown" href="javascript:void(0);" data-toggle="dropdown" aria-expanded="false">
                  {{username}}
                    <!--<div class="nav-profile-img">
                      <img src="https://via.placeholder.com/36X36" alt="image" />
                    </div>-->
                  </span>
                </template>
                <b-dropdown-item class="preview-item">
                  <i class="ti-settings text-primary"></i> <span >Settings</span>
                </b-dropdown-item>
                <b-dropdown-item class="preview-item">
                  <!-- <i class="ti-power-off text-primary"></i> <router-link class="nav-link" to="/auth/signin">Sign Out</router-link> -->
                  <i class="ti-power-off text-primary"></i> <span v-on:click="signOut()">Sign Out</span>
                </b-dropdown-item>
              </b-nav-item-dropdown>
              <b-nav-item class="nav-login d-none d-lg-flex nav-settings" v-if="!isSigned">
                <router-link  class="nav-link" to="/auth/signin">
                  <span >Sign In </span>
                </router-link>
              </b-nav-item>
              <b-nav-item class="nav-logout d-none d-lg-flex nav-settings">
                <i class="ti-more" @click="togglesettingsPannel()"></i>
              </b-nav-item>
            </b-navbar-nav>
            <button
              class="navbar-toggler navbar-toggler-right align-self-center"
              type="button"
              @click="toggleMobileSidebar()"
            >
              <span class="ti-menu"></span>
            </button>
          </div>
        </div>
      </nav>
      <Navisample></Navisample>
      <!-- <Navigator></Navigator>-->
    </div>
  </b-navbar>
</template>

<script>
  import Navisample from "./Navisample";
  // import Navigator from "./Navigator";

export default {
  name: "app-header",
  components: {
    Navisample,
    // Navigator
  },
  data () {
    return {
    }
  },
  computed: {
    isSigned: function () {
      return (this.$cookie.get('authrizeduser') !== null)
    },
    username: function () {
      return this.$store.getters.getUser !== null ? this.$store.getters.getUser.firstname : ''
    }
  },
  mounted() {
    var navItems = document.querySelectorAll(
      ".horizontal-menu .page-navigation >.nav-item"
    );
    document
      .querySelectorAll(".horizontal-menu .page-navigation >.nav-item")
      .forEach(function(el) {
        el.addEventListener("click", function() {
          for (var i = 0; i < navItems.length; i++) {
            if (navItems[i] == el) {
              el.classList.toggle("show-submenu");
            } else {
              navItems[i].classList.remove("show-submenu");
            }
          }
        });
      });
  },
  methods: {
    signOut () {
      this.$http.post(
        '/episode/api/authentication/signout'
      ).then(() => {
        this.$router.push('/auth/signin')
        this.$store.commit('toEmpty')
        this.$cookie.delete('authrizeduser')
      }).catch((error) => {
        console.log(error)
      })
    },
    toggleSidebar: () => {
      document.querySelector("body").classList.toggle("sidebar-icon-only");
    },
    toggleMobileSidebar: () => {
      document
        .querySelector(".bottom-navbar")
        .classList.toggle("header-toggled");
    },
    togglesettingsPannel: () => {
      document.querySelector("#right-sidebar").classList.toggle("open");
    },
    subIsActive(input) {
      const paths = Array.isArray(input) ? input : [input];
      return paths.some(path => {
        return this.$route.path.indexOf(path) === 0; // current path starts with this path string
      });
    },
    handleSCroll() {
      let header = document.querySelector("body");
      if (window.scrollY > 70) {
        header.classList.add("fixed-on-scroll");
      } else {
        header.classList.remove("fixed-on-scroll");
      }
    }
  },
  created() {
    window.addEventListener("scroll", this.handleSCroll);
  },
  destroyed() {
    window.removeEventListener("scroll", this.handleSCroll);
  },
  watch: {
    $route() {
      document
        .querySelector(".bottom-navbar")
        .classList.remove("header-toggled");
    }
  }
};
</script>

<style scoped></style>
