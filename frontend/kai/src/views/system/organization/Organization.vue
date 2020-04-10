<!--
 Class Name  : Organization.vue
 Description : 조직
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.19  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <b-row>
            <div class='col-md-12'>
              <div class="comm-btn-box" role="group">
                <b-button variant="primary" class="px-3 sm" v-on:click="add" v-show="visibleAdd">{{labels.new}}</b-button>
              </div>
            </div>
          </b-row>
          <p class="grid-line"></p>
          <b-row style="padding-top: 20px">
            <div class='col-md-4' style="padding-top:10px">
              <div class="search-component sm">
                <input class="form-control formdata sm" style="width: 100%" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder=labels.search />
              </div>
            </div>
            <div class='col-md-8'>
              <div class="float-right mt-2" role="group" style="margin:0px">
                <el-button v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                  <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                </el-button>
              </div>
            </div>
          </b-row>
          <b-row class='mt-2'>
            <div class='col-md-12'>
              <p class="grid-line"></p>
              <data-tables v-if="listVisiable" ref="orgTable" :current-page.sync='listGrid.currentPage' layout='table, pagination' :table-props='listGrid.tableProps' :data='listGrid.data' :filters='listGrid.filters' :page-size="10"  :pagination-props='listGrid.pageProps' @filtered-data="handleFilteredData" @row-click="openModal">
                <el-table-column prop="id" :label="labels.id" width="80"></el-table-column>
                <el-table-column prop="orgId" :label="labels.orgId" width="200"></el-table-column>
                <el-table-column prop="description" :label="labels.description" width="*"></el-table-column>
                <el-table-column prop="status" :label="labels.status" width="150"></el-table-column>
                <el-table-column prop="createDt" :label="labels.createDt" width="200"><template slot-scope="scope">{{$util.dateTimeWithTimeZone(scope.row.createDt)}}</template></el-table-column>
                <el-table-column prop="createNm" :label="labels.createId" width="150"></el-table-column>
              </data-tables>
            </div>
          </b-row>
          <el-dialog :title="labels.title" :visible.sync="dialogFormVisible">
            <b-row>
              <div class='col'>
                <el-form :model="listGrid.currentData" ref="orgForm" label-width="120px">
                  <b-row class="mt-2">
                    <div class='form-group col-md-6' style="padding-right:1%">
                      <label for="organization_id">{{labels.id}}</label>
                      <input id="organization_id" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                    </div>
                    <div class='form-group col-md-6' style="padding-right:1%">
                      <label for="organization_orgId">{{labels.orgId}}<span class="require-mark">*</span></label>
                      <input id="organization_orgId" class="formdata sm float-right" :placeholder="labels.inputDataMsg" v-on:blur="blurOrgId" style="width: 100%" v-model="listGrid.currentData.orgId" :disabled="this.listGrid.currentData.hasOwnProperty('id')" maxlength="20"/>
                      <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.orgIdMsg)" v-text="validMsg.orgIdMsg"></p>
                    </div>
                  </b-row>
                  <b-row class="mt-2">
                    <div class='form-group col-md-6' style="padding-right:1%">
                      <label for="organization_description">{{labels.description}}<span class="require-mark">*</span></label>
                      <input id="organization_description" class="formdata sm float-right" :placeholder="labels.inputDataMsg" style="width: 100%" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" maxlength="50"/>
                      <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.desMsg)" v-text="validMsg.desMsg"></p>
                    </div>
                    <div class='form-group col-md-6' style="padding-right:1%">
                      <label for="organization_status">{{labels.status}}<span class="require-mark">*</span></label>
                      <el-select id="organization_status" v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" style="width: 100%" >
                        <el-option v-for="status in statusSelect" :prop="status.key" :value="status.key" :key="status.key">
                          {{status.key}}
                        </el-option>
                      </el-select>
                    </div>
                  </b-row>
                  <b-row class="mt-2">
                    <div class='form-group col-md-3' style="padding-right:1%">
                      <label for="organization_createId">{{labels.createId}}</label>
                      <input id="organization_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER" disabled="disabled" />
                    </div>
                    <div class='form-group col-md-3' style="padding-right:1%">
                      <label for="organization_createDt">{{labels.createDt}}</label>
                      <input id="organization_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled"/>
                    </div>
                    <div class='form-group col-md-3' style="padding-right:1%">
                      <label for="organization_updateId">{{labels.updateId}}</label>
                      <input id="organization_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled" />
                    </div>
                    <div class='form-group col-md-3' style="padding-right:1%">
                      <label>{{labels.updateDt}}</label>
                      <input id="organization_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                    </div>
                  </b-row>
                  <!-- org variable -->
                  <div class="col-md-12">
                    <b-row>
                      <div class="col">
                        <div class="float-right mt-3" role="group">
                          <b-button variant="primary" class="px-3 sm" v-on:click="addVar" v-show="visibleAddVar">{{labels.new}}</b-button>
                        </div>
                      </div>
                    </b-row>
                    <b-row class='mt-2'>
                      <div class="form-group col">
                        <p class='grid-line'></p>
                        <data-tables layout='table, pagination' :data='variableGrid.data' :filters='variableGrid.filters' :page-size='variableGrid.pageProps.pageSize' :pagination-props='variableGrid.pageProps'>
                          <el-table-column prop="key" :label="labels.key" >
                            <template slot-scope="props">
                              <input class="form-control formdata sm" type="text" v-model="props.row.key" v-on:change="changeValue(props.row)" :disabled="props.row.hasOwnProperty('id')"/>
                            </template>
                          </el-table-column>
                          <el-table-column prop="value" :label="labels.value" >
                            <template slot-scope="props">
                              <input class="form-control formdata sm" type="text" v-model="props.row.value" v-on:change="changeValue(props.row)"/>
                            </template>
                          </el-table-column>
                          <el-table-column prop="description" :label="labels.description" >
                            <template slot-scope="props">
                              <input class="form-control formdata sm" type="text" v-model="props.row.description" v-on:change="changeValue(props.row)"/>
                            </template>
                          </el-table-column>
                          <el-table-column align="right" width="50">
                            <template slot-scope="props">
                              <el-button v-on:click="delVar(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                                <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                              </el-button>
                            </template>
                          </el-table-column>
                        </data-tables>
                      </div>
                    </b-row>
                  </div>
                  <b-row class="mt-2">
                    <div class="col" style="padding-top:20px">
                      <div class='d-flex justify-content-end'>
                        <b-button variant="primary" class="px-3 sm" v-on:click="save" v-show="visibleSave">{{labels.save}}</b-button>
                        <b-button variant="primary" class="px-3 sm" v-on:click="del" v-show="visibleDelete">{{labels.delete}}</b-button>
                        <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
                      </div>
                    </div>
                  </b-row>
                </el-form>
              </div>
            </b-row>
          </el-dialog>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>
<script>
export default {
  name: 'ORGANIZATION',
  mounted () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.variableGrid.data = []
    this.lables = {}
    this.$nextTick(function () {
      this.listVisiable = false
    })
  },
  data () {
    return {
      listVisiable: true,
      labels: {
        title: '',
        new: '',
        delete: '',
        search: '',
        save: '',
        close: '',
        reset: '',
        fail: '',
        checkData: '',
        deleteSuccess: '',
        id: '',
        orgId: '',
        description: '',
        status: '',
        createId: '',
        createDt: '',
        updateId: '',
        updateDt: '',
        orgIdMessage: '',
        lengthMessage: '',
        excelDownload: '',
        successMessage: '',
        desMessage: ''
      },
      listGrid: {
        currentPage: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        pageProps: {
          pageSizes: [10, 20, 30]
        },
        rowIndex: -1,
        data: [],
        filters: [
          { prop: ['orgId'], value: '' }
        ],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'id', width: '50px' },
          { prop: 'orgId', label: 'orgId', width: '100px' },
          { prop: 'description', label: 'description', width: '100px' },
          { prop: 'status', label: 'status', width: '100px' },
          { prop: 'createDt', label: 'createDt', width: '100px' },
          { prop: 'createNm', label: 'createId', width: '100px' },
          { prop: 'updateDt', label: 'updateDt', width: '100px' },
          { prop: 'updateNm', label: 'updateId', width: '100px' }
        ],
        columns: ['id', 'orgId', 'description', 'status', 'createDt', 'createId', 'updateDt', 'updateId'],
        columnNames: ['id', 'orgId', 'description', 'status', 'createDt', 'createId', 'updateDt', 'updateId'],
        currentData: {
          id: '',
          orgId: '',
          description: '',
          status: '',
          createDt: '',
          createId: '',
          updateDt: '',
          updateId: ''
        },
        searchParam: ''
      },
      variableGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        pageProps: {
          pageSizes: [5, 10, 15]
        },
        rowIndex: -1,
        data: [],
        filters: [
          { prop: ['orgId'], value: '' }
        ],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'id', width: '50px' },
          { prop: 'orgId', label: 'orgId', width: '100px' },
          { prop: 'key', label: 'description', width: '100px' },
          { prop: 'value', label: 'status', width: '100px' }
        ],
        currentData: {}
      },
      // Auth
      authorities: {
        ORGANIZATION_ADD: false,
        ORGANIZATION_SAVE: false,
        ORGANIZATION_DELETE: false,
        ORGANIZATION_VAR_ADD: false,
        ORGANIZATION_VAR_SAVE: false,
        ORGANIZATION_VAR_DELETE: false
      },
      // Global Msg
      validMsg: {
        orgIdMsg: '',
        desMsg: ''
      },
      dialogFormVisible: false,
      validVarFlag: false,
      statusSelect: []
    }
  },
  watch: {
    dialogFormVisible: function (val) {
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
    },
    'listGrid.currentData.orgId': function (val) {
      if (!this.$util.isNull(val) && !this.listGrid.currentData.hasOwnProperty('id')) {
        this.validMsg.orgIdMsg = ''
      }
    },
    'listGrid.currentData.description': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.desMsg = ''
      }
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.ORGANIZATION_ADD
    },
    visibleSave: function () {
      return this.authorities.ORGANIZATION_SAVE
    },
    visibleDelete: function () {
      return this.authorities.ORGANIZATION_DELETE && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAddVar: function () {
      return this.authorities.ORGANIZATION_VAR_ADD
    },
    visibleDeleteVar: function () {
      return this.authorities.ORGANIZATION_VAR_DELETE
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels, this.listGrid.titles)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'statusSelect', domainId: 'ORGSTATUS'}
        ]
      }
      this.$util.getComboData(param)
      this.getListData()
    },
    getListData: function () {
      this.$http.get('/episode/api/system/organization').then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      this.$http.get('/episode/api/system/organization/' + id).then(res => {
        this.listGrid.currentData = res.data
        this.listGrid.rowIndex = this.listGrid.data.indexOf(this.listGrid.currentRow)
      })
      this.$http.get('/episode/api/system/organization/' + id + '/orgvariable').then(res => {
        this.variableGrid.data = res.data
      })
    },
    blurOrgId: function () {
      if (!this.listGrid.currentData.hasOwnProperty('id')) {
        this.$http.get('/episode/api/system/organization?orgId=' + this.listGrid.currentData.orgId).then(res => {
          if (res.data.length > 0 && !this.$util.isNull(this.listGrid.currentData.orgId)) {
            this.validMsg.orgIdMsg = this.labels.checkData
            this.listGrid.currentData.orgId = ''
          } else {
            this.validMsg.orgIdMsg = ''
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    changeValue: function (row) {
      if (!this.$util.isNull(row.id)) {
        row.state = 'CHANGED'
      } else {
        row.state = 'ADDED'
      }
    },
    add: function () {
      this.dialogFormVisible = true
      this.listGrid.currentData = {
        orgId: '',
        description: '',
        status: 'ACTIVE',
        state: 'ADDED'
      }
      this.variableGrid.data = []
    },
    openModal: function (row) {
      this.dialogFormVisible = true
      this.getRowData(row.id)
    },
    close: function () {
      this.dialogFormVisible = false
      this.listGrid.currentData = {}
    },
    isValidationCheck: function () {
      let rtnFlag = true
      let orgId = this.listGrid.currentData.orgId
      let description = this.listGrid.currentData.description
      if (this.$util.isNull(orgId)) {
        this.validMsg.orgIdMsg = this.labels.orgIdMessage
        rtnFlag = false
      }
      if (this.$util.isNull(description)) {
        this.validMsg.desMsg = this.labels.desMessage
        rtnFlag = false
      }
      return rtnFlag
    },
    saveVar: function () {
      this.validVarFlag = false
      let dataList = this.variableGrid.data.filter(data => {
        return data.state === 'CHANGED' || data.state === 'ADDED'
      })
      if (dataList.filter(data => (this.$util.isNull(data.key))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.varKeyMessage)
        this.validVarFlag = true
        return []
      }
      if (dataList.filter(data => (this.$util.isNull(data.value))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.varValueMessage)
        this.validVarFlag = true
        return []
      }
      if (dataList.filter(data => (this.$util.isNull(data.description))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.varDesMessage)
        this.validVarFlag = true
        return []
      }
      for (let i = 0; i < dataList.length; i++) {
        let data = dataList[i]
        if (this.variableGrid.data.filter(datachk => data.key === datachk.key).length > 1) {
          this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
          this.validVarFlag = true
          return []
        }
      }
      return dataList
    },
    save: function () {
      if (this.isValidationCheck()) {
        let variableData = this.saveVar()
        if (this.validVarFlag) return
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/organization/' + this.listGrid.currentData.id : '/episode/api/system/organization/'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || variableData.length > 0) {
          this.listGrid.currentData.orgvariable = variableData
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.successMessage)
            this.getListData()
            this.getRowData(res.data.id)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.fail)
            console.log(error)
          })
        }
      }
    },
    del: function () {
      let $this = this
      this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
        $this.$http.delete('/episode/api/system/organization/' + $this.listGrid.currentData.id).then(res => {
          $this.$util.esmessage($this, 'delete', $this.labels.deleteSuccess)
          $this.getListData()
          $this.dialogFormVisible = false
        }).catch(error => {
          $this.$util.esmessage($this, 'warn', $this.labels.fail)
          console.log(error)
        })
      })
    },
    addVar: function () {
      this.variableGrid.data.splice(0, 0, {
        state: 'ADDED',
        orgId: this.listGrid.currentData.orgId,
        key: '',
        value: '',
        description: ''
      })
    },
    delVar: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/system/organization/orgvariable/' + row.id).then(res => {
          this.variableGrid.data.splice(index, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteSuccess)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.variableGrid.data.splice(index, 1)
      }
    },
    exportFilter: function () {
      let fileName = 'organization'
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'orgId', label: this.labels.orgId},
        {value: 'description', label: this.labels.description},
        {value: 'status', label: this.labels.status},
        {value: 'createDt', label: this.labels.createDt},
        {value: 'createNm', label: this.labels.createId}
      ]
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    }
  }
}
</script>
