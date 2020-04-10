<!--
 File Name  : UserCi.vue
 Description : 사이드 바 > 관심장비 > 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.13  이정호  최초 생성
-->
<template>
  <b-list-group-item class="list-group-item-divider">
    <div class="avatar del-icon cursor-pointer float-right" v-on:click='delUserCi'>
      <i class="fa fa-minus-circle fa-lg"></i>
    </div>
    <el-tooltip class="item" effect="dark" :content="userCiData.ciname" placement="right">
      <div>
        <h6 class="mr-3 ciname">
          {{userCiData.ciname}}
        </h6>
      </div>
    </el-tooltip>
    <small class="text-muted mr-3 ciname">{{userCiData.ciId}} - {{userCiData.classstructureNm}}</small>
    <div  xl class="mb-2 mb-xl-0">
      <b-button size="sm" variant="ghost-dark" v-on:click='topologyOpen'>{{labels.topology}}</b-button>
      <b-button size="sm" variant="ghost-dark" v-on:click='ciOpen'>{{labels.ci}}</b-button>
      <b-button v-show="userCiData.datasrc" size="sm" variant="ghost-dark" v-on:click='monitorOpen'>{{labels.monitor}}</b-button>
    </div>
  </b-list-group-item>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'

export default {
  name: 'USERCI',
  props: ['data', 'labels'],
  data () {
    return {
      userCiData: {},
      badge: '',
      divider: ''
    }
  },
  methods: {
    initialize: function () {
      this.userCiData = this.data
    },
    topologyOpen: function () {
      eventBus.$emit('topologyOpen', this.userCiData)
    },
    monitorOpen: function () {
      this.$util.move(this, 'CIMONITORDETAIL', this.userCiData.idCi)
    },
    ciOpen: function () {
      this.$util.move(this, 'CI', this.userCiData.idCi)
    },
    delUserCi: function () {
      let $this = this
      this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
        let id = $this.userCiData.id
        this.$http.delete(
          '/episode/api/sidebar/userci/' + id
        ).then(res => {
          this.$util.esmessage(this, 'delete', $this.labels.deleteMessage)
          eventBus.$emit('getUserCiList')
        }).catch(error => {
          console.log(error)
        })
      })
    },
    dateTime: function (date) {
      return this.$util.dateTime(date)
    }
  },
  created () {
    this.initialize()
  }
}
</script>
