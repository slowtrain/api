<template>
  <b-tabs v-model="tabIndex">
    <b-tab v-on:click="eventTab">
      <template slot="title">
        <el-tooltip class="item" effect="light" :content="labels.event" placement="bottom">
        <i class='icon-speech'></i>
    </el-tooltip>
      </template>
      <b-list-group class="list-group-accent">
        <b-list-group-item class="list-group-item-accent-secondary bg-light text-center font-weight-bold text-muted text-uppercase small">
          {{labels.event}}
        </b-list-group-item>
          <UserCiEvent v-for='(event, index) in eventList' :key="index" :labels='labels' :data='event' :severityClass='severityColor'></UserCiEvent>
        </b-list-group>
    </b-tab>
    <b-tab v-on:click="ciTab">
      <template slot="title">
        <el-tooltip class="item" effect="light" :content="labels.interestCi" placement="bottom">
        <i class='icon-list'></i>
        </el-tooltip>
      </template>
      <b-list-group class="list-group-accent">
         <b-list-group-item class="list-group-item-accent-secondary sm bg-light font-weight-bold text-uppercase ">
         <div class="float-left" style="padding-top: 3px; color: white;">
           {{labels.interestCi}}
            </div>
         <div class="float-right">
           <div v-on:click="ciRegistrationOpen" class='cursor-pointer' style="padding-bottom: 6px; width:30px; height: 30px;">
             <img src="@/assets/img/icons/plus@2x.png" width="60%" />
           </div>
         </div>
        </b-list-group-item>
        <b-list-group-item class="bg-light text-center font-weight-bold text-muted text-uppercase small">
            <div class="search-component sm">
              <input id="searchUser" type="text" class="formdata sm" v-model="searchParam" v-on:keyup.enter="searchCi" :placeholder="labels.ciSearch" />
            </div>
        </b-list-group-item>
          <UserCi v-for='(userCi, index) in userCiList' :labels='labels' :key="index" :data='userCi'></UserCi>
     </b-list-group>

    </b-tab>

  </b-tabs>
  </template>
<script>
import {eventBus} from '@/eventBus/eventBus'
const UserCiEvent = () => import('@/views/aside/userci/UserCiEvent')
const UserCi = () => import('@/views/aside/userci/UserCi')

export default {
  name: 'DefaultAside',
  props: ['data'],
  components: {
    UserCiEvent, UserCi
  },

  data () {
    return {
      labels: {
        event: '',
        interestCi: '',
        ciSearch: '',
        topology: '',
        monitor: '',
        ci: '',
        confirmDelete: '',
        deleteMessage: ''
      },
      cycle: 60,
      intervalId: null,
      eventList: [],
      userCiList: [],
      tabIndex: 0,
      userName: '',
      searchParam: '',
      severityColor: {}
    }
  },
  methods: {
    initialize: function () {
      this.$http.get('/episode/api/system/common/domain?domainId=SEVERITYCOLOR').then(res => {
        this.setSeverityColor(res.data)
      }).catch(error => {
        console.log(error)
      })
      let orgvariable = this.$store.getters.getOrgvariable
      if (orgvariable.length !== 0) {
        this.rpaCycle = parseInt(orgvariable.filter(r => r.key === 'EVENT')[0].value)
      }
      this.userName = this.$store.getters.getUser.username
      // this.startInterval()
      this.$util.setLabels('ASIDE', this.labels)
    },
    setSeverityColor: function (data) {
      data.forEach(row => {
        this.severityColor[row.key] = row.value
      })
    },
    eventTab: function () {
      this.startInterval()
    },

    ciTab: function () {
      this.getUserCiList()
      this.stopInterval()
    },

    startInterval: function () {
      this.getEventList()
      // this.intervalId = setInterval(this.getEventList, (this.cycle * 1000))
    },

    stopInterval: function () {
      clearInterval(this.intervalId)
      this.eventList = []
    },
    getEventList: function () {
      this.$http.get(
        '/episode/api/sidebar/userEventList'
      ).then(res => {
        this.eventList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getUserCiList: function () {
      let searchParam = this.searchParam.trim()
      this.userCiList = []
      this.$http.get(
        '/episode/api/sidebar/userci', {params: {searchParam: searchParam}}
      ).then(res => {
        this.userCiList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    searchCi: function () {
      this.getUserCiList()
    },
    ciRegistrationOpen: function () {
      eventBus.$emit('cirRgistrationOpen')
    }
  },

  created: function () {
    this.initialize()
  },
  mounted () {
    let $this = this
    eventBus.$off('getUserCiList')
    eventBus.$on('getUserCiList', function () {
      $this.getUserCiList()
    })
  },
  beforeDestroy () {
    clearInterval(this.intervalId)
  }

}
</script>
