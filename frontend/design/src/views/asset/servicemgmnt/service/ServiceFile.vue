<!--
 Class Name  : ServiceFile.vue
 Description : 서비스 > 첨부
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
-->
<template>
  <div>
    <ServiceDetailDefault
      ref="serviceDetailDefault"
      v-if="(activeTab !== 'list') && this.activeTabFlag.file"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:classType="classType"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class="col-md-12">
        <b-row>
          <div class="col" style="padding-top:25px; padding-left:20px;">
            <a class="title-box">{{labels.attachFile}}</a>
          </div>
          <div class="col">
            <div class="float-right mt-3" style="height:34px;" role="group">
            </div>
          </div>
        </b-row>
        <Attachment
          v-if="(activeTab !== 'list') && this.activeTabFlag.file"
          :ownerid='ownerid'
          :ownertable='ownertable'
          :showInputFlag='0'
          ref='attachment'
        />
      </div>
    </b-row>
  </div>
</template>
<script>
import {eventBus} from '@/eventBus/eventBus'
import ServiceDetailDefault from './ServiceDetailDefault'
import Attachment from '@/views/modal/Attachment'
export default {
  props: ['labels', 'activeTab', 'comboData', 'detailType', 'classType', 'listGrid', 'activeTabFlag'],
  components: {
    'ServiceDetailDefault': ServiceDetailDefault,
    'Attachment': Attachment
  },
  watch: {
    'listGrid.currentData.id': function (val) {
      if (!this.$util.isNull(val)) {
        this.ownerid = val
        this.ownertable = 'CI'
      } else {
        if (this.$refs.attachment !== undefined) {
          this.$refs.attachment.attachmentGrid.data = []
        }
      }
    }
  },
  data () {
    return {
      showFiledownFlag: false,
      ownerid: null,
      ownertable: null
    }
  },
  methods: {
    openUploadModal: function (currentData) {
      eventBus.$emit('fileUploadOpen', 'CI', currentData.id)
    }
  },
  created: function () {}
}
</script>
