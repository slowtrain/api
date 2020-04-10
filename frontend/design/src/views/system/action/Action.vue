<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class='row'>
            <div class='col-md-12'>
              <div class="comm-btn-box" role="group">
                <!-- <b-button variant="primary" class="px-3 sm" v-show="visibleDelete" v-on:click="del">{{labels.delete}}</b-button> -->
                <b-button variant="primary" class="px-3 sm" v-show="visibleTest" v-on:click="test">테스트</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleDel" v-on:click="del">{{labels.delete}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
              </div>
            </div>
          </div>
          <p class="grid-line"></p>
          <el-tabs v-model="activeTab" @tab-click="changeTab">
            <el-tab-pane :label="labels.list" name="list">
              <div class='row'>
                <div class='col-md-4' style="padding-top:10px">
                  <div class="search-component sm">
                    <input class="form-control formdata sm" style="width: 100%;"  v-model="listGrid.filters[0].value" :placeholder=labels.searchA />
                  </div>
                </div>
                <div class='col-md-8'>
                  <div class="float-right mt-2" role="group">
                    <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                      <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                    </el-button>
                  </div>
                </div>
              </div>
              <div class='row mt-2'>
                <div class='col'>
                  <p class='grid-line'></p>
                  <data-tables style="width: 100%" layout='table, pagination' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps'
                                v-on:row-click="handleRowClick" v-on:filtered-data="handleFilteredData">
                    <el-table-column prop="id" :label="labels.id" width="80" ></el-table-column>
                    <el-table-column prop="actionId" :label="labels.actionId" width="200" ></el-table-column>
                    <el-table-column prop="description" :label="labels.description" ></el-table-column>
                    <el-table-column prop="objectId" :label="labels.objectId" width="100" ></el-table-column>
                    <el-table-column prop="createDt" :label="labels.createDt" width="200" ><template slot-scope="scope">{{dateTime(scope.row.createDt)}}</template></el-table-column>
                    <el-table-column prop="createId" :label="labels.createId" width="200" ></el-table-column>
                  </data-tables>
                </div>
              </div>
            </el-tab-pane>

            <!--detail-->
            <el-tab-pane :label="labels.detail" name="detail" v-if="(activeTab !== 'list')">
              <div class='row'>
                <div class='col'>
                  <el-form :model="listGrid.currentData"  ref="appForm" label-width="120px" >
                    <div class="form-row mt-2">
                      <div class='form-group col-md-2'>
                        <label for="id">{{labels.id}}</label>
                        <input id="id" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-2'>
                        <label for="actionId">{{labels.actionId}}<span class="require-mark">*</span></label>
                        <input id="actionId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.actionId" placeholder="Action" v-on:blur="validateAction" :disabled="actionDisabled" />
                        <p class="form_some-copy sm error float-right" v-show="showAction"  v-text="actionText"></p>
                      </div>
                      <div class='form-group col-md-2'>
                        <label for="objectId">{{labels.objectId}}<span class="require-mark">*</span></label>
                        <el-select v-model="listGrid.currentData.objectId" style="width:100%" :placeholder="labels.objectId" :disabled="objectDisabled" >
                          <el-option v-for="item in tableSelect" :key="item.tableName" :label="item.tableName" :value="item.tableName" >
                          </el-option>
                        </el-select>
                        <p class="form_some-copy sm error float-right" v-show="showObject"  v-text="objectText"></p>
                      </div>
                      <div class='form-group col-md-2'>
                        <label for="objectId">{{labels.type}}<span class="require-mark">*</span></label>
                        <el-select v-model="listGrid.currentData.type" style="width:100%" :placeholder="labels.type"  >
                          <el-option  key="JYTHON"  value="JYTHON" />
                          <el-option  key="CLASS"  value="CLASS" />
                        </el-select>
                        <p class="form_some-copy sm error float-right" v-show="showObject"  v-text="objectText"></p>
                      </div>
                      <div class='form-group col-md-4'>
                        <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
                        <input id="description" :class="classDes" style="width: 100%" v-on:blur="validateDes" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" />
                        <p class="form_some-copy sm error float-right" v-show="showDes" v-text="desText"></p>
                      </div>
                    </div>
                    <div class="form-row mt-2">
                      <div class='form-group col-md-3'>
                        <label for="action_createId">{{labels.createId}}</label>
                        <input id="action_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createId" placeholder="CREATER"  disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="action_createDt">{{labels.createDt}}</label>
                        <input id="action_createDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="action_updateId">{{labels.updateId}}</label>
                        <input id="action_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateId" placeholder="UPDATER" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label>{{labels.updateDt}}</label>
                        <input id="action_updateDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                      </div>
                    </div>
                  </el-form>
                </div>
              </div>
              <b-row>
                <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <div class='row'>
                <div class='col'>
                  <div v-show="isClass">
                    <label>{{labels.className}}</label>
                    <input id="className" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.className" placeholder="Class name"/>
                  </div>
                  <div v-show="isJython">
                    <label>{{labels.jython}}</label>
                    <el-input type="textarea" :rows="20" placeholder="Jython code" v-model="listGrid.currentData.jython">
                    </el-input>
                  </div>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </b-col>
      </b-row>
      <el-dialog :title="labels.actionTestTitle" :visible.sync="actionTestVisible" width="30%" >
        <el-form ref="inputForm" label-width="120px">
          <b-row class="mt-2">
            <div class="col-md-4">
              <label>테이블 아이디 입력</label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="testData.id" />
            </div>
          </b-row>
          <b-row class="mt-2">
            <div class="col-md-12">
                <el-input type="textarea" :rows="10"  v-model="testData.result" readonly >
                </el-input>
            </div>
          </b-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <b-button class='px-3 sm' variant="primary" v-on:click="actionTestExecute">테스트</b-button>
          <b-button class='px-3 sm' variant="primary" v-on:click="actionTestClose">닫기</b-button>
        </span>
      </el-dialog>
    </b-card>
  </div>
</template>

<script>
export default {
  name: 'ACTION',
  data () {
    return {
      actionTestVisible: false,
      testData: {
        id: null,
        result: null
      },
      isProcess: false,
      activeTab: 'list',
      labels: {
        title: '',
        new: '',
        delete: '',
        search: '',
        close: '',
        list: '',
        detail: '',
        save: '',
        name: '',
        status: '',
        id: '',
        actionId: '',
        description: '',
        expression: '',
        createId: '',
        createDt: '',
        updateId: '',
        updateDt: '',
        lang: '',
        searchMessage: '',
        nameExists: '',
        authExists: '',
        saveMessage: '',
        failMessage: '',
        deleteMessage: '',
        authnameMessage: '',
        descriptionMessage: '',
        langcodeMessage: '',
        childDataExists: '',
        yesMessage: '',
        noMessage: '',
        appidMessage: '',
        searchA: '',
        lengthMessage: '',
        lengthMessageA: '',
        desMessage: '',
        labelnameMessage: '',
        excelDownload: ''
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 20, 30]
        },
        data: [],
        currentData: {
        },
        rowIndex: -1,
        filters: [{
          prop: ['actionId', 'objectId', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'Id' },
          { prop: 'actionId', label: 'Action' },
          { prop: 'objectId', label: 'Object' },
          { prop: 'description', label: 'Description' },
          { prop: 'createId', label: 'Creater' },
          { prop: 'createDt', label: 'Create Date' }
        ],
        columns: [ 'actionId', 'objectId', 'description', 'createId', 'createDt' ],
        columnNames: [ 'actionId', 'objectId', 'description', 'createId', 'createDt' ]
      },
      tableSelect: [],
      typeSelect: [{type: 'JYTHON'}, {type: 'CLASS'}],
      classAction: 'formdata sm float-right',
      showAction: false,
      actionText: '',
      classObject: 'formdata sm float-right',
      showObject: false,
      objectText: '',
      classDescription: 'formdata sm float-right',
      showDes: false,
      desText: '',
      authorities: {
        ACTION_ADD: false,
        ACTION_SAVE: false,
        ACTION_DELETE: false,
        ACTION_TEST: false
      }
    }
  },
  watch: {
    'listGrid.currentData.status': function (newValue, oldValue) {
      if (this.listGrid.currentData.isAlive && newValue !== 'ACTIVE') {
        this.listGrid.currentData.status = oldValue
        this.$util.esmessage(this, 'warn', this.labels.canNotChangeStatus)
      }
    },
    'listGrid.currentData.objectId': function (newValue, oldValue) {
      if (!(newValue === undefined || newValue === '')) {
        this.classObject = 'formdata sm good float-right'
        this.showObject = false
        this.objectText = ''
      }
    },
    'listGrid.currentData.description': function (newValue, oldValue) {
      if (!(newValue === undefined || newValue === '')) {
        this.classDes = 'formdata sm good float-right'
        this.showDes = false
        this.desText = ''
      }
    }
  },
  computed: {
    isJython: function () {
      return this.listGrid.currentData.type === 'JYTHON'
    },
    isClass: function () {
      return this.listGrid.currentData.type === 'CLASS'
    },
    actionDisabled: function () {
      return this.listGrid.currentData.hasOwnProperty('id')
    },
    objectDisabled: function () {
      return this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleTest: function () {
      let current = this.listGrid.currentData
      return (this.activeTab !== 'list') && current.hasOwnProperty('id') && current.jython !== null && current.jython !== ''
    },
    visibleSave: function () {
      return (this.activeTab !== 'list')
    },
    visibleAdd: function () {
      return (this.activeTab === 'list')
    },
    visibleDel: function () {
      return (this.activeTab !== 'list')
    }
  },
  methods: {
    test: function () {
      console.log('@@@@@@ jython : ', this.listGrid.currentData.jython)
      this.actionTestVisible = true
      this.testData.id = null
      this.testData.result = null
    },
    actionTestClose: function () {
      this.testData.result = ''
      this.actionTestVisible = false
    },
    actionTestExecute: function () {
      this.$http.post(
        '/episode/api/system/action/' + this.listGrid.currentData.id + '/test/' + this.testData.id
      ).then(res => {
        this.testData.result = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.$http.get(
        '/episode/api/system/common/table'
      ).then(res => {
        this.tableSelect = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/action'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    init: function (id, appId) {
      this.classAction = 'formdata sm float-right'
      this.showAction = false
      this.classDes = 'formdata sm float-right'
      this.showDes = false
      this.$http.get(
        '/episode/api/system/action/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    changeTab: function (tab, event) {
      this.activeTab = tab.name
      if (tab.name === 'list') {
        this.listGrid.currentData = {}
        this.classAction = 'formdata sm float-right'
        this.showAction = false
        this.classDes = 'formdata sm float-right'
        this.showDes = false
      }
    },
    validateAction: function () {
      if (!this.listGrid.currentData.hasOwnProperty('id')) {
        if (this.listGrid.currentData.actionId === undefined || this.listGrid.currentData.actionId === '') {
          this.listGrid.currentData.actionId = ''
          this.classAction = 'formdata sm error float-right'
          this.showAction = true
          this.actionText = this.labels.actionEmptyMessage
          return false
        }
        this.$http.get(
          '/episode/api/system/action?actionId=' + this.listGrid.currentData.actionId
        ).then(res => {
          if (res.data.length > 0) {
            this.listGrid.currentData.actionId = ''
            this.classAction = 'formdata sm error float-right'
            this.showAction = true
            this.actionText = this.labels.labelActionMessage
            return false
          }
          this.classAction = 'formdata sm good float-right'
          this.showAction = false
          return true
        }).catch(() => {
          this.listGrid.currentData.actionId = ''
          this.classAction = 'formdata sm error float-right'
          this.showAction = true
          this.actionText = this.labels.labelActionMessage
          return false
        })
      }
    },
    validateObject: function () {
      if (this.listGrid.currentData.objectId === undefined || this.listGrid.currentData.objectId === '') {
        this.classObject = 'formdata sm error float-right'
        this.showObject = true
        this.objectText = this.labels.objectMessage
        return false
      }
      this.classObject = 'formdata sm good float-right'
      this.showObject = false
      return true
    },
    validateDes: function () {
      if (this.listGrid.currentData.description === undefined || this.listGrid.currentData.description === '') {
        this.classDes = 'formdata sm error float-right'
        this.showDes = true
        this.desText = this.labels.desMessage
        return false
      }
      this.classDes = 'formdata sm good float-right'
      this.showDes = false
      return true
    },
    changeValue: function (data) {
      if (this.listGrid.currentData.id !== undefined || this.listGrid.currentData.id !== '') {
        this.listGrid.currentData.state = 'CHANGED'
      } else {
        this.listGrid.currentData.state = 'ADDED'
      }
    },

    // APPLICATION (add / save / delete)
    add: function () {
      this.listGrid.currentData = {status: 'ACTIVE', state: 'ADDED', type: 'JYTHON'}
      this.activeTab = 'detail'
      this.classDes = 'formdata sm float-right'
      this.showDes = false
    },
    save: function () {
      let validate = true
      validate = this.validateAction()
      validate = this.validateObject()
      validate = this.validateDes()
      if (validate) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/action/' + this.listGrid.currentData.id : '/episode/api/system/action'
        this.$http({ method: method, url: url, data: this.listGrid.currentData
        }).then(res => {
          if (method === 'POST') {
            this.listGrid.data.unshift(res.data)
            this.listGrid.rowIndex = 0
          } else {
            this.listGrid.data.splice(this.listGrid.rowIndex, 1, res.data)
          }
          this.listGrid.currentData = res.data
          this.listGrid.currentData.state = undefined
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
    del: function () {
      if (this.listGrid.currentData.id === undefined || this.listGrid.currentData.id === '') {
        this.activeTab = 'list'
      } else {
        this.$http.delete('/episode/api/system/action/' + this.listGrid.currentData.id).then(res => {
          this.listGrid.data.splice(this.listGrid.rowIndex, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.activeTab = 'list'
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },

    // excel download
    exportFilter: function () {
      let lang = this.$store.getters.getUser.lang
      let fileName = 'Action'
      if (lang === 'ko-kr') {
        fileName = '액션'
      }
      this.$util.csvExport(this.listGrid.filteredData, this.listGrid.columns, this.listGrid.columnNames, fileName)
    },
    dateTime: function (date) {
      return this.$util.dateTimeWithTimeZone(date)
    },
    handleRowClick: function (row, column, event) {
      this.activeTab = 'detail'
      this.listGrid.rowIndex = this.listGrid.data.indexOf(row)
      this.init(row.id, row.appId)
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    handleModuleSelectionChange: function (val) {
      this.moduleSearchGrid.selection = val
    }
  },
  mounted () {
    this.initialize()
  }
}
</script>
