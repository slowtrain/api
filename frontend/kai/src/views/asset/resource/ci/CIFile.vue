<!--
 Class Name  : CIFile.vue
 Description : IT 자원 > 첨부
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
    <CIDetailDefault
      ref="ciDetailDefault"
      v-if="(activeTab !== 'list') && this.activeTabFlag.file"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
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
import CIDetailDefault from './CIDetailDefault'
import Attachment from '@/views/modal/Attachment'
export default {
  props: ['activeTab', 'labels', 'comboData', 'detailType', 'listGrid', 'activeTabFlag'],
  components: {
    'CIDetailDefault': CIDetailDefault,
    'Attachment': Attachment
  },
  watch: {
    'listGrid.currentData.id': function (val) {
      if (!this.$util.isNull(val)) {
        this.ownerid = val
        this.ownertable = 'CI'
      } else {
        this.$refs.attachment.attachmentGrid.data = []
      }
    }
  },
  data () {
    return {
      tabActiveFlag: false,
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
