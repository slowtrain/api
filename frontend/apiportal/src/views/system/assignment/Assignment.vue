<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class='row'>
            <div class='col-md-12'>
              <div class="comm-btn-box" role="group">
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
                    <input class="form-control formdata sm" style="width: 100%;"  v-model="listGrid.filters[0].value"  />
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
                    <el-table-column prop="assignmentId" :label="labels.assignmentId" width="200" ></el-table-column>
                    <el-table-column prop="description" :label="labels.description" ></el-table-column>
                    <el-table-column prop="type" :label="labels.type" width="100" ></el-table-column>
                    <el-table-column prop="target" :label="labels.target" width="100" ></el-table-column>
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
                      <!--
                      <div class='form-group col-md-2'>
                        <label for="id">{{labels.id}}</label>
                        <input id="id" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                      </div>
                      -->
                      <div class='form-group col-md-2'>
                        <label for="assignmentId">{{labels.assignmentId}}<span class="require-mark">*</span></label>
                        <input id="assignmentId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.assignmentId" placeholder="assignment" v-on:blur="validateAssignment" :disabled="assignmentDisabled" />
                        <p class="form_some-copy sm error float-right" v-show="showAssignment"  v-text="assignmentText"></p>
                      </div>
                      <div class='form-group col-md-2'>
                        <label for="type">{{labels.type}}<span class="require-mark">*</span></label>
                        <el-select v-model="listGrid.currentData.type" style="width:100%" :placeholder="labels.type"  >
                          <el-option v-for="item in typeSelect" :key="item.key" :label="item.key" :value="item.key" >
                          </el-option>
                        </el-select>
                        <p class="form_some-copy sm error float-right" v-show="showObject"  v-text="objectText"></p>
                      </div>
                      <div class='form-group col-md-2'>
                        <label for="assignment_target">{{labels.target}}<span class="require-mark">*</span></label>
                        <input id="assignment_target" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.target" placeholder="target"  />
                        <p class="form_some-copy sm error float-right" v-show="showTarget"  v-text="targetText"></p>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
                        <input id="description" :class="classDes" style="width: 100%" v-on:blur="validateDes" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" />
                        <p class="form_some-copy sm error float-right" v-show="showDes" v-text="desText"></p>
                      </div>
                    </div>
                    <div class="form-row mt-2">
                      <div class='form-group col-md-3'>
                        <label for="assignment_createId">{{labels.createId}}</label>
                        <input id="assignment_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createId" placeholder="CREATER"  disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="assignment_createDt">{{labels.createDt}}</label>
                        <input id="assignment_createDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="assignment_updateId">{{labels.updateId}}</label>
                        <input id="assignment_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateId" placeholder="UPDATER" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label>{{labels.updateDt}}</label>
                        <input id="assignment_updateDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                      </div>
                    </div>
                  </el-form>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
export default {
  name: 'ASSIGNMENT',
  data () {
    return {
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
        assignmentId: '',
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
          prop: ['assignmentId', 'type', 'target', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'Id' },
          { prop: 'assignmentId', label: 'assignment' },
          { prop: 'type', label: 'Type' },
          { prop: 'target', label: 'Target' },
          { prop: 'description', label: 'Description' },
          { prop: 'createId', label: 'Creater' },
          { prop: 'createDt', label: 'Create Date' }
        ],
        columns: [ 'assignmentId', 'type', 'target', 'description', 'createId', 'createDt' ],
        columnNames: [ 'assignmentId', 'type', 'target', 'description', 'createId', 'createDt' ]
      },
      typeSelect: [],
      classAssignment: 'formdata sm float-right',
      showAssignment: false,
      assignmentText: '',
      classTarget: 'formdata sm float-right',
      showTarget: false,
      targetText: '',
      classDescription: 'formdata sm float-right',
      showDes: false,
      desText: '',
      authorities: {
        ASSIGNMENT_ADD: false,
        ASSIGNMENT_SAVE: false,
        ASSIGNMENT_DELETE: false
      }
    }
  },
  watch: {
    // eslint-disable-next-line no-unused-vars
    'listGrid.currentData.target': function (newValue, oldValue) {
      if (!(newValue === undefined || newValue === '')) {
        this.classTarget = 'formdata sm good float-right'
        this.showTarget = false
        this.targetText = ''
      }
    },
    // eslint-disable-next-line no-unused-vars
    'listGrid.currentData.description': function (newValue, oldValue) {
      if (!(newValue === undefined || newValue === '')) {
        this.classDes = 'formdata sm good float-right'
        this.showDes = false
        this.desText = ''
      }
    }
  },
  computed: {
    assignmentDisabled: function () {
      return this.listGrid.currentData.hasOwnProperty('id')
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
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.$http.get(
        '/episode/api/system/common/domain?domainId=ASSIGNMENT_TYPE'
      ).then(res => {
        this.typeSelect = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/assignment'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // eslint-disable-next-line no-unused-vars
    init: function (id, appId) {
      this.classAssignment = 'formdata sm float-right'
      this.showAssignment = false
      this.classDes = 'formdata sm float-right'
      this.showDes = false
      this.$http.get(
        '/episode/api/system/assignment/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // eslint-disable-next-line no-unused-vars
    changeTab: function (tab, event) {
      this.activeTab = tab.name
      if (tab.name === 'list') {
        this.listGrid.currentData = {}
        this.classAssignment = 'formdata sm float-right'
        this.showAssignment = false
        this.classDes = 'formdata sm float-right'
        this.showDes = false
      }
    },
    validateAssignment: function () {
      if (!this.listGrid.currentData.hasOwnProperty('id')) {
        if (this.listGrid.currentData.assignmentId === undefined || this.listGrid.currentData.assignmentId === '') {
          this.listGrid.currentData.assignmentId = ''
          this.classAssignment = 'formdata sm error float-right'
          this.showAssignment = true
          this.assignmentText = this.labels.assignmentEmptyMessage
          return false
        }
        this.$http.get(
          '/episode/api/system/assignment?assignmentId=' + this.listGrid.currentData.assignmentId
        ).then(res => {
          if (res.data.length > 0) {
            this.listGrid.currentData.assignmentId = ''
            this.classAssignment = 'formdata sm error float-right'
            this.showAssignment = true
            this.assignmentText = this.labels.labelassignmentMessage
            return false
          }
          this.classAssignment = 'formdata sm good float-right'
          this.showAssignment = false
          return true
        }).catch(() => {
          this.listGrid.currentData.assignmentId = ''
          this.classAssignment = 'formdata sm error float-right'
          this.showAssignment = true
          this.assignmentText = this.labels.labelassignmentMessage
          return false
        })
      }
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
    // eslint-disable-next-line no-unused-vars
    changeValue: function (data) {
      if (this.listGrid.currentData.id !== undefined || this.listGrid.currentData.id !== '') {
        this.listGrid.currentData.state = 'CHANGED'
      } else {
        this.listGrid.currentData.state = 'ADDED'
      }
    },

    // APPLICATION (add / save / delete)
    add: function () {
      this.listGrid.currentData = {status: 'ACTIVE', state: 'ADDED'}
      this.activeTab = 'detail'
      this.classDes = 'formdata sm float-right'
      this.showDes = false
    },
    save: function () {
      let validate = true
      validate = this.validateAssignment()
      validate = this.validateDes()
      if (validate) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/assignment/' + this.listGrid.currentData.id : '/episode/api/system/assignment'
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
        // eslint-disable-next-line no-unused-vars
        this.$http.delete('/episode/api/system/assignment/' + this.listGrid.currentData.id).then(res => {
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
      let fileName = 'assignment'
      if (lang === 'ko-kr') {
        fileName = '액션'
      }
      this.$util.csvExport(this.listGrid.filteredData, this.listGrid.columns, this.listGrid.columnNames, fileName)
    },
    dateTime: function (date) {
      return this.$util.dateTimeWithTimeZone(date)
    },
    // eslint-disable-next-line no-unused-vars
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
