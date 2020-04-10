<template>
  <el-dialog title="워크플로우 프로세스 선택" :visible="true" width="500px" :show-close="false">
      <div>
        <div style="margin-top: 20px" v-for="item in wfprocesses"  :key="item.id" :label="item.wfprocessId" >
          <el-radio v-model="wfprocessUid" :label="item.id" >{{item.description}}</el-radio>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <b-button class='px-3 sm' variant="primary" v-on:click="progress">시작</b-button>
        <b-button class='px-3 sm' variant="primary" v-on:click="close">닫기</b-button>
      </span>
    </el-dialog>
</template>

<script>
export default {
  name: 'WFPROCESS',
  props: ['data', 'callbackFunc'],
  data () {
    return {
      wfprocesses: [],
      wfprocessUid: null
    }
  },
  methods: {
    progress: function () {
      this.$util.wfprogress(this, this.data.objectId, this.data.uid, this.wfprocessUid, null, this.callbackFunc)
    },
    close: function () {
      this.$emit('wfprocessClose')
    }
  },
  mounted () {
    this.$http.get(
      '/episode/api/system/common/workflow/wfprocess', {params: {objectId: this.data.objectId}}
    ).then(res => {
      this.wfprocesses = res.data.wfprocesses
      if (this.wfprocesses.length > 0) {
        this.wfprocessUid = this.wfprocesses[0].id
      }
    })
  }
}
</script>
