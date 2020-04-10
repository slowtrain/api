<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class='row'>
            <div class='col-md-12'>
              <div class="comm-btn-box" role="group">
                <!-- <b-button variant="primary" class="px-3 sm" v-show="visibleDelete" v-on:click="del">{{labels.delete}}</b-button> -->
                <b-button variant="primary" class="px-3 sm" v-show="visibleExecute" v-on:click="execute">{{labels.execute}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleOn" v-on:click="on">{{labels.on}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleOff" v-on:click="off">{{labels.off}}</b-button>
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
                    <input class="form-control formdata sm" style="width: 100%;"  v-model="listGrid.filters[0].value" :placeholder="labels.searchA" />
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
                                v-on:row-click="handleRowClick" v-on:filtered-data="handleFilteredData" v-if="listGridFlag">
                    <el-table-column prop="id" :label="labels.id" width="80" ></el-table-column>
                    <el-table-column prop="schedulerId" :label="labels.schedulerId" width="200" ></el-table-column>
                    <el-table-column prop="description" :label="labels.description" width="300" ></el-table-column>
                    <el-table-column prop="status" :label="labels.status" width="100" ></el-table-column>
                    <el-table-column prop="expression" :label="labels.expression" width="100" ></el-table-column>
                    <el-table-column prop="parameter" :label="labels.parameter" width="*" ></el-table-column>
                    <!--
                    <el-table-column prop="createDt" :label="labels.createDt" width="200" ><template slot-scope="scope">{{dateTime(scope.row.createDt)}}</template></el-table-column>
                    <el-table-column prop="createId" :label="labels.createId" width="200" ></el-table-column>
                    -->
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
                      <div class='form-group col-md-3'>
                        <label for="id">{{labels.id}}</label>
                        <input id="id" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="schedulerId">{{labels.schedulerId}}<span class="require-mark">*</span></label>
                        <el-select id="schedulerId" v-model="listGrid.currentData.schedulerId"  v-on:change="validateScheduler" placeholder="Please select a schedulerId" style="width: 100%;" :disabled="this.listGrid.currentData.hasOwnProperty('id')">
                          <el-option v-for="scheduler in schedulerSelect"  :value="scheduler.schedulerId" :key="scheduler.schedulerId" :label="scheduler.schedulerId"></el-option>
                        </el-select>
                        <p class="form_some-copy sm error float-right" v-show="showScheduler" v-text="schedulerText"></p>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
                        <input id="description" :class="classDes" style="width: 100%" v-on:blur="validateDes" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" />
                        <p class="form_some-copy sm error float-right" v-show="showDes" v-text="desText"></p>
                      </div>
                    </div>
                    <div class="form-row mt-2">
                      <div class='form-group col-md-2'>
                        <label for="scheduler_status">{{labels.status}}<span class="require-mark">*</span></label>
                        <el-select id="scheduler_status" v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" placeholder="Please select a STATUS" style="width: 100%;" >
                          <el-option  value="ACTIVE" key="ACTIVE">ACTIVE</el-option>
                          <el-option  value="INACTIVE" key="INACTIVE">INACTIVE</el-option>
                        </el-select>
                      </div>
                      <div class='form-group col-md-2'>
                         <div class="search-component sm">
                          <label for="scheduler_expression">{{labels.expression}}<span class="require-mark">*</span></label>
                          <input id="scheduler_expression" placeholder="Seconds Minutes Hours Day Month Week on UTC"
                          :class="classExpression" style="width: 100%" v-on:blur="validateExpression" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.expression" />
                          <p class="form_some-copy sm error float-right" v-show="showExpression" v-text="expressionText"></p>
                        </div>
                      </div>
                      <div class='form-group col-md-2'>
                          <label for="scheduler_quantity">{{labels.quantity}}<span class="require-mark">*</span></label>
                          <input id="scheduler_quantity" placeholder="0 - 1000"
                          :class="classQuantity" style="width: 100%" v-on:blur="validateQuantity" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.historyQuantity" />
                          <p class="form_some-copy sm error float-right" v-show="showQuantity" v-text="quantityText"></p>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="scheduler_parameter">{{labels.parameter}}</label>
                        <input id="scheduler_parameter" :class="classParameter" style="width: 100%"  v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.parameter" placeholder="parameter" />
                      </div>
                    </div>
                    <div class="form-row mt-2">
                      <div class='form-group col-md-3'>
                        <label for="scheduler_createId">{{labels.createId}}</label>
                        <input id="scheduler_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="scheduler_createDt">{{labels.createDt}}</label>
                        <input id="scheduler_createDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="scheduler_updateId">{{labels.updateId}}</label>
                        <input id="scheduler_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label>{{labels.updateDt}}</label>
                        <input id="scheduler_updateDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                      </div>
                    </div>
                    <div class="form-row mt-2">
                      <div class='form-group col-md-12'>
                        <label>{{labels.isAlive}}</label>
                        <el-switch v-model="listGrid.currentData.isAlive" disabled="disabled" ></el-switch>
                      </div>
                    </div>
                  </el-form>
                </div>
              </div>

              <div class='row mt-2'>
                <div class="col" style="border: 0.5px solid #c8d3df;"></div>
              </div>
              <div class='row mt-2'>
                <!--history-->
                <div class="col-md-6">
                  <div class='row'>
                    <div class="col" style="padding-top:20px; padding-bottom:4px; padding-left:20px;">
                      <a class="title-box">{{labels.history}}</a>
                    </div>
                  </div>
                  <div class='row mt-2'>
                    <div class="col">
                      <p class="grid-line"></p>
                      <data-tables ref="historyTable" layout='table, pagination' v-bind:data='historyGrid.data' v-bind:filters='historyGrid.filters' v-bind:page-size='historyGrid.pageProps.pageSize' v-bind:pagination-props='historyGrid.pageProps' >
                        <el-table-column prop="ipAddress" v-bind:label="labels.ipAddress"></el-table-column>
                        <el-table-column prop="startDt" :label="labels.startDt" ><template slot-scope="scope">{{dateTime(scope.row.startDt)}}</template></el-table-column>
                        <el-table-column prop="endDt" :label="labels.endDt" ><template slot-scope="scope">{{dateTime(scope.row.endDt)}}</template></el-table-column>
                      </data-tables>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class='row'>
                    <div class="col" style="padding-top:20px; padding-bottom:4px; padding-left:20px;">
                      <a class="title-box">{{labels.action}}</a>
                    </div>
                  </div>
                  <div class='row mt-2'>
                    <div class="col">
                      <p class="grid-line"></p>
                      <data-tables ref="historyTable" layout='table, pagination' v-bind:data='actionGrid.data' v-bind:filters='actionGrid.filters' v-bind:page-size='actionGrid.pageProps.pageSize' v-bind:pagination-props='historyGrid.pageProps' >
                        <el-table-column prop="action" v-bind:label="labels.action"></el-table-column>
                        <el-table-column prop="createId" :label="labels.actionBy" ></el-table-column>
                        <el-table-column prop="createDt" :label="labels.actionDate" ><template slot-scope="scope">{{dateTime(scope.row.createDt)}}</template></el-table-column>
                      </data-tables>
                    </div>
                  </div>
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
  name: 'SCHEDULER',
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
        schedulerId: '',
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
          prop: ['schedulerId', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'Id' },
          { prop: 'schedulerId', label: 'Scheduler' },
          { prop: 'description', label: 'Description' },
          { prop: 'expression', label: 'expression' },
          { prop: 'parameter', label: 'paramter' },
          { prop: 'createId', label: 'Creater' },
          { prop: 'createDt', label: 'Create Date' }
        ],
        columns: [ 'schedulerId', 'description', 'expression', 'parameter', 'createId', 'createDt' ],
        columnNames: [ 'schedulerId', 'description', 'expression', 'parameter', 'createId', 'createDt' ]
      },
      historyGrid: {
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        titles: [
          { prop: 'ipAddress', label: 'ipAddress' },
          { prop: 'startDt', label: 'startDt' },
          { prop: 'endDt', label: 'endDt' }
        ],
        columns: [ 'ipAddress', 'startDt', 'endDt' ],
        columnNames: [ 'ipAddress', 'startDt', 'endDt' ]
      },
      actionGrid: {
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        titles: [
          { prop: 'action', label: 'action' },
          { prop: 'createId', label: 'createId' },
          { prop: 'createDt', label: 'createDt' }
        ],
        columns: [ 'action', 'createId', 'createDt' ],
        columnNames: [ 'action', 'createId', 'createDt' ]
      },
      listGridFlag: false,
      schedulerSelect: [],
      classScheduler: 'formdata sm float-right',
      showScheduler: false,
      schedulerText: '',
      classDescription: 'formdata sm float-right',
      showDes: false,
      desText: '',
      classExpression: 'formdata sm float-right',
      showExpression: false,
      expressionText: '',
      classQuantity: 'formdata sm float-right',
      showQuantity: false,
      quantityText: '',
      classParameter: 'formdata sm float-right',
      authorities: {
        SCHEDULER_ADD: false,
        SCHEDULER_SAVE: false,
        SCHEDULER_DELETE: false,
        SCHEDULER_START: false,
        SCHEDULER_STOP: false,
        SCHEDULER_EXECUTE: false
      }
    }
  },
  watch: {
    'listGrid.currentData.status': function (newValue, oldValue) {
      if (this.listGrid.currentData.isAlive && newValue !== 'ACTIVE') {
        this.listGrid.currentData.status = oldValue
        this.$util.esmessage(this, 'warn', this.labels.canNotChangeStatus)
      }
    }
  },
  computed: {
    visibleExecute: function () {
      return this.authorities.SCHEDULER_EXECUTE && !this.listGrid.currentData.isAlive && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleOn: function () {
      return this.authorities.SCHEDULER_START && !this.listGrid.currentData.isAlive && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleOff: function () {
      return this.authorities.SCHEDULER_STOP && this.listGrid.currentData.isAlive && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleDelete: function () {
      return this.authorities.SCHEDULER_DELETE && (this.activeTab !== 'list')
    },
    visibleSave: function () {
      return this.authorities.SCHEDULER_SAVE && (this.activeTab !== 'list')
    },
    visibleAdd: function () {
      return this.authorities.SCHEDULER_ADD && (this.activeTab === 'list')
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
        '/episode/api/system/scheduler/available'
      ).then(res => {
        this.schedulerSelect = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/scheduler'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
      this.listGridFlag = true
    },
    init: function (id, appId) {
      this.classExpression = 'formdata sm float-right'
      this.showExpression = false
      this.classScheduler = 'formdata sm float-right'
      this.showScheduler = false
      this.classDes = 'formdata sm float-right'
      this.showDes = false
      this.classQuantity = 'formdata sm float-right'
      this.showQuantity = false
      this.$http.get(
        '/episode/api/system/scheduler/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get(
        '/episode/api/system/scheduler/' + id + '/history'
      ).then(res => {
        this.historyGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get(
        '/episode/api/system/scheduler/' + id + '/actionhistory'
      ).then(res => {
        this.actionGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    changeTab: function (tab, event) {
      this.activeTab = tab.name
      if (tab.name === 'list') {
        this.listGrid.currentData = {}
        this.classScheduler = 'formdata sm float-right'
        this.classDes = 'formdata sm float-right'
        this.classExpression = 'formdata sm float-right'
        this.showScheduler = false
        this.showExpression = false
        this.showDes = false
      }
    },
    execute: function () {
      this.$http.post(
        '/episode/api/system/scheduler/' + this.listGrid.currentData.id + '/execute'
      ).then(() => {
        this.$util.esmessage(this, 'check', this.labels.executeMessage)
        this.$http.get(
          '/episode/api/system/scheduler/' + this.listGrid.currentData.id + '/actionhistory'
        ).then(res => {
          this.actionGrid.data = res.data
        })
      })
    },
    on: function () {
      this.$http.get('/episode/api/system/scheduler/' + this.listGrid.currentData.id).then((res) => {
        if (this.listGrid.currentData.status === 'ACTIVE' && res.data.status === 'ACTIVE') {
          this.$http.post(
            '/episode/api/system/scheduler/' + this.listGrid.currentData.id + '/start'
          ).then(() => {
            this.listGrid.currentData.isAlive = true
            this.$util.esmessage(this, 'check', this.labels.startMessage)
            this.$http.get(
              '/episode/api/system/scheduler/' + this.listGrid.currentData.id + '/actionhistory'
            ).then(res => {
              this.actionGrid.data = res.data
            })
          })
        } else {
          this.$util.esmessage(this, 'warn', this.labels.canNotStart)
        }
      })
    },
    off: function () {
      this.$http.get('/episode/api/system/scheduler/' + this.listGrid.currentData.id).then((res) => {
        if (this.listGrid.currentData.status === 'ACTIVE' && res.data.status === 'ACTIVE') {
          this.$http.post(
            '/episode/api/system/scheduler/' + this.listGrid.currentData.id + '/stop'
          ).then(() => {
            this.listGrid.currentData.isAlive = false
            this.$util.esmessage(this, 'check', this.labels.stopMessage)
            this.$http.get(
              '/episode/api/system/scheduler/' + this.listGrid.currentData.id + '/actionhistory'
            ).then(res => {
              this.actionGrid.data = res.data
            })
          })
        } else {
          this.$util.esmessage(this, 'warn', this.labels.canNotStop)
        }
      })
    },
    validateScheduler: function () {
      if (!this.listGrid.currentData.hasOwnProperty('id')) {
        if (this.listGrid.currentData.schedulerId === undefined || this.listGrid.currentData.schedulerId === '') {
          this.listGrid.currentData.schedulerId = ''
          this.classScheduler = 'formdata sm error float-right'
          this.showScheduler = true
          this.schedulerText = this.labels.schedulerEmptyMessage
          return false
        }
        return this.$http.get(
          '/episode/api/system/scheduler?schedulerId=' + this.listGrid.currentData.schedulerId
        ).then(res => {
          if (res.data.length > 0) {
            this.listGrid.currentData.schedulerId = ''
            this.classScheduler = 'formdata sm error float-right'
            this.showScheduler = true
            this.schedulerText = this.labels.labelSchedulerMessage
            return false
          }
          this.classScheduler = 'formdata sm good float-right'
          this.showScheduler = false
          return true
        }).catch(() => {
          this.listGrid.currentData.schedulerId = ''
          this.classScheduler = 'formdata sm error float-right'
          this.showScheduler = true
          this.schedulerText = this.labels.labelSchedulerMessage
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
    validateQuantity: function () {
      let setValidate = () => {
        this.classQuantity = 'formdata sm error float-right'
        this.showQuantity = true
        this.quantityText = this.labels.quantityMessage
        this.listGrid.currentData.historyQuantity = ''
        return false
      }
      let quantity = this.listGrid.currentData.historyQuantity
      if (quantity === undefined || quantity === '') {
        return setValidate()
      }
      if (!/^[0-9]*$/.test(quantity)) {
        return setValidate()
      }
      quantity *= 1
      if (quantity < 0 || quantity > 1000) {
        return setValidate()
      }
      this.classQuantity = 'formdata sm good float-right'
      this.showQuantity = false
      return true
    },
    validateExpression: function () {
      let setValidate = () => {
        this.classExpression = 'formdata sm error float-right'
        this.showExpression = true
        this.expressionText = this.labels.expressionMessage
        this.listGrid.currentData.expression = ''
        return false
      }
      let expression = this.listGrid.currentData.expression
      if (expression === undefined || expression === '') {
        return setValidate()
      }
      expression = expression.trim()
      let elements = expression.split(' ')
      if (elements.length !== 6) {
        return setValidate()
      }
      if (!elements.every(element => { return (/^[0-9\s/*-,?LW#]*$/.test(element)) })) {
        return setValidate()
      }
      this.classExpression = 'formdata sm good float-right'
      this.showExpression = false
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
      this.listGrid.currentData = {status: 'ACTIVE', state: 'ADDED'}
      this.historyGrid.data = []
      this.activeTab = 'detail'
      this.classScheduler = 'formdata sm float-right'
      this.showScheduler = false
      this.classDes = 'formdata sm float-right'
      this.showDes = false
      this.classExpression = 'formdata sm float-right'
      this.showExpression = false
      this.classQuantity = 'formdata sm float-right'
      this.showQuantity = false
    },
    save: function () {
      let validate = true
      validate = this.validateScheduler()
      validate = this.validateDes()
      validate = this.validateExpression()
      validate = this.validateQuantity()
      if (validate) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/scheduler/' + this.listGrid.currentData.id : '/episode/api/system/scheduler'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            if (method === 'PUT') {
              this.listGrid.data.splice(this.listGrid.rowIndex, 1, res.data)
            } else {
              this.listGrid.data.push(res.data)
            }
            // this.listGrid.data.push(res.data)
            // this.listGrid.data.splice(this.listGrid.rowIndex, 1, res.data)
            this.listGrid.currentData.state = undefined
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }
    },
    del: function () {
      if (this.listGrid.currentData.id === undefined || this.listGrid.currentData.id === '') {
        this.activeTab = 'list'
      } else if (this.appLabelGrid.data.length > 0 || this.moduleGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        this.$http.delete('/episode/api/system/scheduler/' + this.listGrid.currentData.id).then(res => {
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
      let fileName = 'Scheduler'
      if (lang === 'ko-kr') {
        fileName = '스케줄러'
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
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.historyGrid.data = []
    this.actionGrid.data = []
    this.schedulerSelect = []
    this.labels = {}
    this.authorities = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
