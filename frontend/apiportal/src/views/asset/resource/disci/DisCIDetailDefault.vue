<template>
  <div>
    <b-row>
      <div class="form-group col-md-4">
        <label for="disciId">{{labels.ciId}}</label>
        <input id="disciId" v-model="listGrid.currentData.disciId" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-4">
        <label for="datasrckey1">{{labels.datasrckey}}</label>
        <input id="datasrckey1" v-model="listGrid.currentData.datasrckey1" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label for="datasrc">{{labels.datasrc}}</label>
        <input id="datasrc" v-model="listGrid.currentData.datasrc" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label for="discitype">{{labels.discitype}}</label>
        <input id="discitype" v-model="listGrid.currentData.discitype" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label for="disciname">{{labels.name}}</label>
        <input id="disciname" v-model="listGrid.currentData.disciname" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label for="disciclass">{{labels.classstructureId}}</label>
        <input id="disciclass" v-model="listGrid.currentData.disciclass" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-4">
        <label for="discidescription">{{labels.description}}</label>
        <input id="discidescription" v-model="listGrid.currentData.discidescription" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label for="location">{{labels.locationId}}</label>
        <input id="location" v-model="listGrid.currentData.location" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label for="disciId">{{labels.ciid}}</label>
        <div class="search-component sm">
          <input id="disciId" v-model="listGrid.currentData.ciId" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <el-popover placement="right" width="120" trigger="click">
            <el-button size="mini" type="text" v-on:click="openCIDialog" v-show="visibleCIMap">{{labels.matchCI}}</el-button>
            <br  v-show="visibleCIMap && this.listGrid.currentData.ciId">
            <el-button size="mini" type="text" v-show="this.listGrid.currentData.ciId" v-on:click="delCI(listGrid.currentData)">{{labels.delCI}}</el-button>
            <br  v-show="this.listGrid.currentData.ciId">
            <el-button size="mini" type="text" v-show="this.listGrid.currentData.ciId" v-on:click="moveCI">{{labels.moveCI}}</el-button>
            <a slot="reference" class="action-button cursor-pointer" v-show="visibleCIMap || this.listGrid.currentData.ciId">search</a>
          </el-popover>
        </div>
      </div>
    </b-row>
    <!-- CI Dialog -->
    <CI
      ref="ciDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showCIDialogFlag"
      v-bind:param="{classObj: 'CI', ciDialogType: 'disci', ciDialogParam: listGrid.currentData.datasrc}"
      v-on:add="addCI"
      v-on:close="closeCIDialog"
    />
  </div>
</template>
<script>
import CI from '@/views/common/CI'
export default {
  props: ['labels', 'authorities', 'listGrid'],
  components: {
    'CI': CI
  },
  data () {
    return {
      showCIDialogFlag: false
    }
  },
  computed: {
    visibleCIMap: function () {
      return this.authorities.DISCI_CI_MAP_SAVE
    }
  },
  methods: {
    openCIDialog: function () {
      this.showCIDialogFlag = true
    },
    addCI: function (row) {
      if (Object.keys(row).length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.listGrid.currentData.ciId = row.ciId
      this.$http.put(
        '/episode/api/asset/resource/disci/' + this.listGrid.currentData.id,
        this.listGrid.currentData
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.showCIDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    delCI: function (row) {
      this.listGrid.currentData.ciId = null
      this.$http.put(
        '/episode/api/asset/resource/disci/' + this.listGrid.currentData.id,
        this.listGrid.currentData
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.showCIDialogFlag = false
      }).catch(error => {
        console.log(error)
      })
    },
    closeCIDialog: function () {
      this.showCIDialogFlag = false
    },
    moveCI: function () {
      this.$http.get(
        '/episode/api/asset/resource/ci/id?orgId=' + this.$store.getters.getUser.orgId + '&ciId=' + this.listGrid.currentData.ciId
      ).then(res => {
        this.$util.move(this, 'CI', res.data)
      }).catch(error => {
        console.log(error)
      })
    }
  },
  created: function () {}
}
</script>
