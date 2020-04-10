<template>
  <div class="app">
    <AppHeader fixed>
      <SidebarToggler class="d-lg-none" display="md" mobile />
      <b-link class="navbar-brand" to="/">
    <!--
      <img class="navbar-brand-full" src="../assets/img/brand/logo.svg" width="89" height="25" alt="CoreUI Logo">
      <img class="navbar-brand-minimized" src="../assets/img/brand/sygnet.svg" width="30" height="30" alt="CoreUI Logo">
    -->
        <img class="navbar-brand-full" src="../assets/img/ICAM_LOGO.svg" width="130" height="35" alt="KAI ITSM">
        <!-- <img class="navbar-brand-full" src="../assets/img/logo.png" width="130" height="35" alt="KAI ITSM"> -->
        <img class="navbar-brand-minimized" src="../assets/img/logoK.png" width="30" height="30" alt="KAI ITSM">
      </b-link>
      <SidebarToggler ref='togside' class="d-md-down-none" :defaultOpen="true" />
      <!--
      <b-navbar-nav class="d-md-down-none">
        <b-nav-item class="px-3" to="/dashboard">Dashboard</b-nav-item>
        <b-nav-item class="px-3" to="/users" exact>Users</b-nav-item>
        <b-nav-item class="px-3">Settings</b-nav-item>
      </b-navbar-nav>
      -->
      <NaviTop v-if="false" />
      <!-- <img class="navbar-brand-full" src="../assets/img/ICAM.png" width="198" height="43" alt="KAI ITSM"> -->
      <!-- <img class="navbar-brand-full" src="../assets/img/ICAM_LOGO.svg" width="200" height="65" alt="KAI ITSM"> -->
      <b-navbar-nav class="ml-auto">
        <!--
        <b-nav-item class="d-md-down-none">
          <i class="icon-bell"></i>
          <b-badge pill variant="danger">5</b-badge>
        </b-nav-item>
        <b-nav-item class="d-md-down-none">
          <i class="icon-list"></i>
        </b-nav-item>
         <b-nav-item class="d-md-down-none">
          <i class="icon-location-pin"></i>
        </b-nav-item>
        -->
        <DefaultHeaderDropdownAccnt v-on:changePwOpen='changePwOpen'/>
      </b-navbar-nav>
      <div v-on:click='asideToggle'>
      <AsideToggler ref='tog' class="d-none d-lg-block" :defaultOpen="true" />
      </div>
      <!--<AsideToggler class="d-lg-none" mobile />-->
    </AppHeader>
    <div class="app-body">
      <AppSidebar fixed>
        <SidebarHeader/>
        <SidebarForm/>
        <SidebarNav :navItems="nav"></SidebarNav>
        <SidebarFooter/>
        <SidebarMinimizer/>
      </AppSidebar>
      <main class="main">
        <b-breadcrumb :items="items"/>
        <!-- <Breadcrumb :list="list"/> -->
        <div class="container-fluid">
          <router-view></router-view>
        </div>
      </main>
      <AppAside ref='test' fixed>
        <!--aside-->
        <DefaultAside ref='aside' :data='asideOpen'/>
      </AppAside>
      <Topology v-if="topologyFlag" v-on:topologyClose='topologyClose' :ciData="ciData" />
      <TopologyService v-if="TopologyServiceFlag" v-on:TopologyServiceClose='TopologyServiceClose' :ciData="ciData" />
      <CiRegistration v-if="ciRegistrationFlag" v-on:ciRegistrationClose='ciRegistrationClose'/>
      <FileUpload v-if="fileUploadFlag" :ownertable="ownertable" :ownerid="ownerid" v-on:fileUploadClose='fileUploadClose'/>
      <EventDetail ref="eventDetail"/>
      <CiEventList ref="ciEventList"/>
      <ChangePw v-if="dialogFormFlag" v-on:changePwClose='changePwClose' v-on:changeFlag='changeFlag' v-bind:labels="labels" v-bind:dateFlag="dateFlag" ref='changePw'/>
      <WFProcess v-if="wfprocessFlag" v-on:wfprocessClose='wfprocessClose' :data="data" :callbackFunc="callbackFunc"/>
      <WFOption v-if="wfoptionFlag" v-on:wfoptionClose='wfoptionClose' :data="data" :callbackFunc="callbackFunc"/>
    </div>
  </div>
</template>

<script>
// import nav from '@/_nav'
import { Header as AppHeader, SidebarToggler, Sidebar as AppSidebar, SidebarFooter, SidebarForm, SidebarHeader, SidebarMinimizer, SidebarNav, Aside as AppAside, AsideToggler, Breadcrumb } from '@coreui/vue'
import DefaultAside from './DefaultAside'
import DefaultHeaderDropdownAccnt from './DefaultHeaderDropdownAccnt'
import {eventBus} from '@/eventBus/eventBus'
import ChangePw from './ChangePw'
import CiRegistration from '@/views/aside/userci/CiRegistration'
import Topology from '@/views/common/Topology'
import TopologyService from '@/views/common/TopologyService'
import EventDetail from '@/views/common/EventDetail'
import CiEventList from '@/views/common/CiEventList'
import FileUpload from '@/views/modal/FileUpload'
import WFOption from '@/views/common/WFOption'
import WFProcess from '@/views/common/WFProcess'

import NaviTop from './NaviTop'

export default {
  name: 'DefaultContainer',
  components: {
    AsideToggler,
    AppHeader,
    AppSidebar,
    AppAside,
    Breadcrumb,
    DefaultAside,
    DefaultHeaderDropdownAccnt,
    SidebarForm,
    SidebarFooter,
    SidebarToggler,
    SidebarHeader,
    SidebarNav,
    SidebarMinimizer,
    CiRegistration,
    ChangePw,
    Topology,
    EventDetail,
    CiEventList,
    FileUpload,
    TopologyService,
    WFOption,
    WFProcess,
    NaviTop
  },
  data () {
    return {
      nav: [],
      menus: [],
      asideOpen: true,
      dialogFormFlag: false,
      topologyFlag: false,
      ciRegistrationFlag: false,
      TopologyServiceFlag: false,
      wfoptionFlag: false,
      wfprocessFlag: false,
      ciData: {},
      fileUploadFlag: false,
      ownertable: '',
      ownerid: '',
      dateFlag: false,
      labels: {
        chagnePwTitle: '',
        firstname: '',
        username: '',
        newPassword: '',
        confirmNewPassword: '',
        pwConfirm: '',
        save: '',
        close: '',
        pwMsg: '',
        pwlengthMessage: '',
        pwSameMessage: '',
        pwtextMessage: '',
        saveMessage: '',
        failMessage: '',
        pwNecessary: '',
        pwChangeMessage: '',
        pwBeforeMessage: '',
        pwEnter: '',
        ciMonitorDetail: '',
        newPwMsg: '',
        pwChk: '',
        confirmPw: ''
      }
    }
  },
  computed: {
    name () {
      return this.$route.name
    },
    items () {
      let routes = this.$route.matched.filter((route) => route.name || route.meta.label)
      routes.forEach(route => {
        let filteredRoutes = this.menus.filter((menu) => route.name === menu.navigatorId)
        if (filteredRoutes.length > 0) {
          route.text = filteredRoutes[0].name
          route.href = '#' + route.path
        }
      })
      return routes
    }
    /*
    list () {
      return this.$route.matched.filter((route) => route.name || route.meta.label)
    }
    */
  },
  mounted () {
    let user = this.$store.getters.getUser
    // 최초, 3개월 비밀번호 변경로직
    // if (!this.$util.isNull(user)) {
    //   if (user.changepassword) {
    //     this.dialogFormFlag = true
    //   }
    //   this.$http.get(
    //     '/episode/api/sidebar/userpw/'
    //   ).then(res => {
    //     if (this.$util.isNull(res.data)) {
    //       user.changepassword = true
    //       this.dialogFormFlag = true
    //     } else {
    //       const dateType = res.data.createDt.substring(0, res.data.createDt.indexOf('.'))
    //       let dt = new Date(dateType)
    //       dt = new Date(Date.UTC(dt.getFullYear(), dt.getMonth() + 3, dt.getDate(), dt.getHours(), dt.getMinutes(), dt.getSeconds()))
    //       if (new Date() > dt) {
    //         this.dialogFormFlag = true
    //         this.dateFlag = true
    //       }
    //     }
    //   }).catch(error => {
    //     console.log(error)
    //   })
    // }
    let lang = (user === null) ? 'en-us' : user.lang
    this.$http.get(
      '/episode/api/system/navigator/mainNaviList?userId=' + user.username + '&lang=' + lang
    ).then(res => {
      this.nav = res.data.mainNaviList
      this.menus = res.data.mainList
      this.menus.push({navigatorId: 'CIMONITORDETAIL', name: this.labels.ciMonitorDetail})
    }).catch(error => {
      console.log(error)
    })
    setTimeout(() => {
      let togside = document.getElementsByClassName('sidebar-lg-show')
      let tog = document.getElementsByClassName('aside-menu-lg-show')
      if (this.$util.isNull(togside[0])) {
        if (!this.$util.isNull(this.$refs.togside)) {
          this.$refs.togside.toggle()
        }
      }
      if (this.$util.isNull(tog[0])) {
        if (!this.$util.isNull(this.$refs.tog)) {
          this.$refs.tog.toggle()
        }
      }
    }, 300)
    this.$http.get('/episode/api/system/common/orgvariable'
    ).then(res => {
      this.$store.commit('setOrgvariable', res.data)
    }).catch(error => {
      console.log(error)
    })
    let $this = this
    eventBus.$off('topologyOpen')
    eventBus.$on('topologyOpen', function (ciData) {
      $this.topologyFlag = true
      $this.ciData = ciData
    })
    eventBus.$off('TopologyServiceOpen')
    eventBus.$on('TopologyServiceOpen', function (ciData) {
      $this.TopologyServiceFlag = true
      $this.ciData = ciData
    })
    eventBus.$off('cirRgistrationOpen')
    eventBus.$on('cirRgistrationOpen', function () {
      $this.ciRegistrationFlag = true
    })
    eventBus.$off('fileUploadOpen')
    eventBus.$on('fileUploadOpen', function (ownertable, ownerid) {
      $this.fileUploadFlag = true
      $this.ownertable = ownertable
      $this.ownerid = ownerid
    })
    eventBus.$off('dialogClose')
    eventBus.$on('dialogClose', function () {
      $this.ciRegistrationFlag = false
      $this.dialogFormFlag = false
      $this.topologyFlag = false
      $this.fileUploadFlag = false
      $this.TopologyServiceFlag = false
      $this.$refs.eventDetail.dialogFormVisible = false
      $this.$refs.ciEventList.dialogFormVisible = false
    })
    eventBus.$off('wfoptionOpen')
    eventBus.$on('wfoptionOpen', function (data, callbackFunc) {
      $this.wfoptionFlag = true
      $this.data = data
      $this.callbackFunc = callbackFunc
    })
    eventBus.$off('wfprocessOpen')
    eventBus.$on('wfprocessOpen', function (data, callbackFunc) {
      $this.wfprocessFlag = true
      $this.data = data
      $this.callbackFunc = callbackFunc
    })
  },

  methods: {
    initialize: function () {
      this.$util.setLabels('HOME', this.labels)
    },
    changePwClose: function () {
      this.dialogFormFlag = false
    },
    changePwOpen: function () {
      this.dialogFormFlag = true
    },
    changeFlag: function () {
      this.dateFlag = false
    },
    asideToggle: function () {
      if (this.asideOpen) {
        this.asideOpen = false
        this.$refs.aside.stopInterval()
      } else {
        this.asideOpen = true
        if (this.$refs.aside.tabIndex === 0) this.$refs.aside.startInterval()
        else this.$refs.aside.getUserCiList()
      }
    },
    topologyClose: function () {
      this.topologyFlag = false
    },
    ciRegistrationClose: function () {
      this.ciRegistrationFlag = false
    },
    fileUploadClose: function () {
      this.fileUploadFlag = false
    },
    TopologyServiceClose: function () {
      this.TopologyServiceFlag = false
    },
    wfoptionClose: function () {
      this.wfoptionFlag = false
    },
    wfprocessClose: function () {
      this.wfprocessFlag = false
    }
  },
  created () {
    this.initialize()
  },
  beforeDestroy () {
  }
}
</script>
