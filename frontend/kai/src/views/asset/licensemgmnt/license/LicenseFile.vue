<!--
 Class Name  : LicenseFile.vue
 Description : 라이선스 > 첨부
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.16  최영훈  최초 생성
-->
<template>
  <div>
    <LicenseDetailDefault
      ref="licenseDetailDefault"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:listGrid="listGrid"
      v-bind:validMsg="validMsg"
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
import LicenseDetailDefault from './LicenseDetailDefault'
import Attachment from '@/views/modal/Attachment'
export default {
  props: ['labels', 'comboData', 'detailType', 'validMsg', 'listGrid'],
  components: {
    'LicenseDetailDefault': LicenseDetailDefault,
    'Attachment': Attachment
  },
  watch: {
    'listGrid.currentData.id': function (val) {
      if (!this.$util.isNull(val)) {
        this.ownerid = val
        this.ownertable = 'LICENSE'
      } else {
        this.$refs.attachment.attachmentGrid.data = []
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
      eventBus.$emit('fileUploadOpen', 'LICENSE', currentData.id)
    }
  },
  created: function () {}
}
</script>
