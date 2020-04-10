<template>
  <el-dialog :title="currentWFProcess.description" :visible="true" width="500px" :show-close="false">
      <div>
        <div style="margin-top: 20px" v-for="item in currentWFConnectors"  :key="item.id" :label="item.wfconnectorId" >
          <el-radio v-model="connectorUid" :label="item.id" >{{item.annotations_content}}</el-radio>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <b-button class='px-3 sm' variant="primary" v-on:click="progress">실행</b-button>
        <b-button class='px-3 sm' variant="primary" v-on:click="close">닫기</b-button>
      </span>
    </el-dialog>
</template>

<script>
export default {
  name: 'WFOPTION',
  props: ['data', 'callbackFunc'],
  data () {
    return {
      currentWFInstance: null,
      currentWFProcess: null,
      currentWFNode: null,
      currentWFConnectors: [],
      connectorUid: null,
      mainObject: {}
    }
  },
  methods: {
    progress: function () {
      this.$util.wfprogress(this, this.data.objectId, this.data.uid, null, this.connectorUid, this.callbackFunc)
    },
    close: function () {
      this.$emit('wfoptionClose')
    }
  },
  mounted () {
    this.$http.get(
      '/episode/api/system/common/workflow/initialize', {params: {objectId: this.data.objectId, uid: this.data.uid}}
    ).then(res => {
      this.currentWFInstance = res.data.currentWFInstance
      this.currentWFProcess = res.data.currentWFProcess
      this.currentWFNode = res.data.currentWFNode
      this.currentWFConnectors = res.data.currentWFConnectors
      if (this.currentWFConnectors.length > 0) {
        this.connectorUid = this.currentWFConnectors[0].id
      }
    })
  }
}
</script>
