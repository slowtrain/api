<template>
  <div class="animated fadeIn">
    <b-card>
      <div slot="header"><h5>{{labels.title}}</h5></div>
      <b-row>
        <b-col sm="12">
            <div class='row'>
        <div class='col-md-12'>
          <div class="btn-group float-right mt-2" role="group">
            <a class="cs-btn purple sm" v-show="delShow&&(activeTab !== 'list')" v-on:click="del">{{labels.delete}}</a>
            <a class="cs-btn purple sm" v-show="saveShow&&(activeTab !== 'list')" v-on:click="save">{{labels.save}}</a>
            <a class="cs-btn purple sm" v-show="saveShow&&(activeTab === 'list')" v-on:click="add">{{labels.new}}</a>
          </div>
        </div>
      </div>

      <el-tabs v-model="activeTab" @tab-click="changeTab">
        <el-tab-pane :label="labels.list" name="list">
          <div class='row'>
            <div class='col-md-4' style="padding-top:10px">
              <div class="search-component sm">
                <input class="formdata sm" style="width: 100%;"  v-model="listGrid.filters[0].value" :placeholder=labels.searchA />
              </div>
            </div>
            <div class='col-md-8'>
              <div class="btn-group float-right mt-2" role="group">
                <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;" v-show="saveShow">
                  <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                </el-button>
              </div>
            </div>
          </div>
          <div class='row mt-2'>
            <div class='col'>
              <p style="border: thin solid #dbdbe0; margin-top:15px;"></p>
              <data-tables style="width: 100%" layout='table, pagination' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps'
                            v-on:row-click="handleRowClick"
                            v-on:filtered-data="handleFilteredData">
                <el-table-column prop="id" :label="labels.id" width="80" align="center"></el-table-column>
                <el-table-column prop="appId" :label="labels.appId" width="200" align="center"></el-table-column>
                <el-table-column prop="description" :label="labels.description" width="*" align="center"></el-table-column>
                <el-table-column prop="url" :label="labels.url" width="250" align="center"></el-table-column>
                <el-table-column prop="createDt" :label="labels.createDt" width="200" align="center"><template slot-scope="scope">{{dateTime(scope.row.createDt)}}</template></el-table-column>
                <el-table-column prop="createId" :label="labels.createId" width="200" align="center"></el-table-column>
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
                  <div class='col-md-3'>
                    <label for="application_id">{{labels.id}}</label>
                    <input id="application_id" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                  </div>
                  <div class='col-md-3'>
                    <label for="application_name">{{labels.name}}<span class="require-mark">*</span></label>
                    <input id="application_name" :class="className" v-on:blur="blurName" style="width: 100%" v-model="listGrid.currentData.appId" :placeholder="labels.searchMessage" :disabled="listGrid.currentData.id !== undefined" >
                    <p class="form_some-copy sm error float-right" v-show="showName" v-text="nameText"></p>
                  </div>
                  <div class='col-md-6'>
                    <label for="application_description">{{labels.description}}<span class="require-mark">*</span></label>
                    <input id="application_description" :class="classDes" style="width: 100%" v-on:blur="blurDes" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" />
                    <p class="form_some-copy sm error float-right" v-show="showDes" v-text="desText"></p>
                  </div>
                </div>
                <div class="form-row mt-2">
                  <div class='col-md-6'>
                    <label for="application_status">{{labels.status}}<span class="require-mark">*</span></label>
                    <el-select id="application_status" v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" placeholder="Please select a STATUS" style="width: 100%;" >
                      <el-option  value="ACTIVE" key="ACTIVE">ACTIVE</el-option>
                      <el-option  value="INACTIVE" key="INACTIVE">INACTIVE</el-option>
                    </el-select>
                  </div>
                  <div class='col-md-6'>
                    <label for="application_url">{{labels.url}}<span class="require-mark">*</span></label>
                    <input id="application_url" :class="classUrl" style="width: 100%" v-on:blur="blurUrl" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.url" placeholder="PATH" />
                    <p class="form_some-copy sm error float-right" v-show="showUrl" v-text="urlText"></p>
                  </div>
                </div>
                <div class="form-row mt-2">
                  <div class='col-md-3'>
                    <label for="application_createId">{{labels.createId}}</label>
                    <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createId" placeholder="CREATER"  disabled="disabled"/>
                  </div>
                  <div class='col-md-3'>
                    <label for="application_createDt">{{labels.createDt}}</label>
                    <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                  </div>
                  <div class='col-md-3'>
                    <label for="application_updateId">{{labels.updateId}}</label>
                    <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateId" placeholder="UPDATER" disabled="disabled"/>
                  </div>
                  <div class='col-md-3'>
                    <label>{{labels.updateDt}}</label>
                    <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                  </div>
                </div>
              </el-form>
            </div>
          </div>

          <div class='row mt-2'>
            <div class="col" style="border: 0.5px solid #c8d3df;"></div>
          </div>
          <div class='row mt-2'>
            <!--AUTH-->
            <div class="col-md-6">
              <div class='row'>
                <div class="col" style="padding-top:20px; padding-left:20px;">
                  <a style="height:36px; font-family: sans-serif; padding:8px 38px; border: 1px solid #9678ff; border-radius:5px; font-size:13px; text-align:center; letter-spacing:1.2px; font-weight:bold;">{{labels.applicationOption}}</a>
                </div>
              </div>
              <div class='row mt-2'>
                <div class="col">
                  <p style="border: thin solid #dbdbe0; margin-top:15px;"></p>
                  <data-tables ref="authTable" layout='table, pagination' v-bind:data='authorityGrid.data' v-bind:filters='authorityGrid.filters' v-bind:page-size='authorityGrid.pageProps.pageSize' v-bind:pagination-props='authorityGrid.pageProps' >
                    <el-table-column prop="appId" v-bind:label="labels.authorityId"></el-table-column>
                    <el-table-column prop="description" v-bind:label="labels.description"></el-table-column>
                  </data-tables>
                </div>
              </div>
            </div>
            <!--Apply Module-->
            <div class="col-md-6">
              <div class='row'>
                <div class="col" style="padding-top:20px; padding-left:20px;">
                  <a style="height:36px; font-family: sans-serif; padding:8px 38px; border: 1px solid #9678ff; border-radius:5px; font-size:13px; text-align:center; letter-spacing:1.2px; font-weight:bold;">{{labels.module}}</a>
                </div>
                <div class="col">
                  <div class="btn-group float-right mt-2" role="group">
                    <a class="cs-btn purple sm" v-on:click="addModule" :disabled="(!listGrid.currentData.hasOwnProperty('id')||isProcess)" v-show="saveShow&&(listGrid.currentData.hasOwnProperty('id'))">{{labels.addModule}}</a>
                  </div>
                </div>
              </div>
              <div class='row mt-2'>
                <div class="col">
                  <p style="border: thin solid #dbdbe0; margin-top:15px;"></p>
                  <data-tables ref="moduleTable" layout='table, pagination' :data='moduleGrid.data' :filters='moduleGrid.filters' :page-size='moduleGrid.pageProps.pageSize' :pagination-props='moduleGrid.pageProps' >
                    <el-table-column prop="module_id" :label="labels.module"></el-table-column>
                    <el-table-column prop="description" :label="labels.description"></el-table-column>
                    <el-table-column align="right" width="80">
                      <template slot-scope="props">
                        <el-button  v-on:click="delModule(props.$index, props.row)" :title="labels.delete" v-show="delShow" style="padding: 0; width:30px; height: 30px;">
                          <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                        </el-button>
                      </template>
                    </el-table-column>
                  </data-tables>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>

        <!-- label tab  -->
        <el-tab-pane :label="labels.label" name="label" v-if="(activeTab !== 'list')">
          <div class='row'>
            <div class='col'>
              <el-form :model="listGrid.currentData"  ref="appForm" label-width="120px" >
                <div class="form-row mt-2">
                  <div class='col-md-3'>
                    <label>{{labels.id}}</label>
                    <input class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                  </div>
                  <div class='col-md-3'>
                    <label>{{labels.name}}<span class="require-mark">*</span></label>
                    <input :class="className" v-on:blur="blurName" style="width: 100%" v-model="listGrid.currentData.appId" :placeholder="labels.searchMessage" :disabled="listGrid.currentData.id !== undefined" />
                    <p class="form_some-copy sm error float-right" v-show="showName" v-text="nameText"></p>
                  </div>
                  <div class='col-md-6'>
                    <label>{{labels.description}}<span class="require-mark">*</span></label>
                    <input :class="classDes" style="width: 100%" v-on:blur="blurDes" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" />
                    <p class="form_some-copy sm error float-right" v-show="showDes" v-text="desText"></p>
                  </div>
                </div>
                <div class="form-row mt-2">
                  <div class='col-md-6'>
                    <label>{{labels.status}}<span class="require-mark">*</span></label>
                    <el-select id="application_status" v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" placeholder="Please select a STATUS" style="width: 100%" >
                      <el-option  value="ACTIVE" key="ACTIVE" >ACTIVE</el-option>
                      <el-option  value="INACTIVE" key="INACTIVE">INACTIVE</el-option>
                    </el-select>
                  </div>
                  <div class='col-md-6'>
                    <label>{{labels.url}}<span class="require-mark">*</span></label>
                    <input :class="classUrl" style="width: 100%"  v-on:blur="blurUrl" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.url" placeholder="PATH"/>
                    <p class="form_some-copy sm error float-right" v-show="showUrl" v-text="urlText"></p>
                  </div>
                </div>
                <div class="form-row mt-2">
                  <div class='col-md-3'>
                    <label>{{labels.createId}}</label>
                    <input class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createId" placeholder="CREATER"  disabled="disabled"/>
                  </div>
                  <div class='col-md-3'>
                    <label>{{labels.createDt}}</label>
                    <input class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                  </div>
                  <div class='col-md-3'>
                    <label>{{labels.updateId}}</label>
                    <input class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateId" placeholder="UPDATER" disabled="disabled"/>
                  </div>
                  <div class='col-md-3'>
                    <label>{{labels.updateDt}}</label>
                    <input class="formdata sm float-right" style="width: 100%" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                  </div>
                </div>
              </el-form>
            </div>
          </div>
          <!-- label grid  -->
          <div class='row mt-2'>
            <div class="col" style="border: 0.5px solid #c8d3df;"></div>
          </div>

          <div class='row mt-2'>
            <div class="col-md-12">
              <div class='row'>
                <div class="col" style="padding-top:20px; padding-left:20px;">
                <div class="search-component sm">
                  <input class="formdata sm" style="width: 100%;"  v-model="appLabelGrid.filters[0].value" :placeholder=labels.searchA />
              </div>
                </div>
                <div class="col">
                  <div class="btn-group float-right mt-2" role="group">
                    <a class="cs-btn purple sm" v-on:click="addLabel" :disabled="(!listGrid.currentData.hasOwnProperty('id')||isProcess)" v-show="saveShow&&(listGrid.currentData.hasOwnProperty('id'))">{{labels.new}}</a>
                    <a class="cs-btn purple sm" v-on:click="saveLabel" :disabled="(!listGrid.currentData.hasOwnProperty('id')||isProcess)" v-show="saveShow&&(listGrid.currentData.hasOwnProperty('id'))">{{labels.save}}</a>
                  </div>
                </div>
              </div>
              <div class='row mt-2'>
                <div class="col">
                  <p style="border: thin solid #dbdbe0; margin-top:15px;"></p>
                  <data-tables ref="labelTable" layout='table, pagination' :data='appLabelGrid.data' :filters='appLabelGrid.filters' :page-size='appLabelGrid.pageProps.pageSize' :pagination-props='appLabelGrid.pageProps'>
                    <el-table-column prop="name" :label="labels.name" >
                      <template slot-scope="props">
                        <input class="form-control formdata sm" type="text" placeholder="Label name" v-model="props.row.name" :disabled="props.row.hasOwnProperty('id')" />
                      </template>
                    </el-table-column>
                    <el-table-column prop="description" :label="labels.description" >
                      <template slot-scope="props">
                        <input class="form-control formdata sm" type="text" placeholder="Description" v-model="props.row.description" :disabled="props.row.hasOwnProperty('id')" />
                      </template>
                    </el-table-column>
                    <el-table-column prop="lang" :label="labels.lang" >
                      <template slot-scope="props">
                        <input class="form-control formdata sm" type="text" placeholder="Language Code" v-model="props.row.lang" v-show="props.row.hasOwnProperty('id')" :disabled="props.row.hasOwnProperty('id')" />
                        <el-select style="width: 100%" v-model="props.row.lang" v-show="!(props.row.hasOwnProperty('id'))">
                          <el-option  value="en-us" key="en-us" >en-us</el-option>
                          <el-option  value="ko-kr" key="ko-kr">ko-kr</el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column align="right" width="80">
                      <template slot-scope="props">
                        <el-button  v-on:click="delLabel(props.$index, props.row)" :title="labels.delete" v-show="delShow" style="padding: 0; width:30px; height: 30px;">
                          <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                        </el-button>
                      </template>
                    </el-table-column>
                  </data-tables>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
        <!-- module Add Dialog  -->
        <el-dialog :title="labels.moduleDialogTitle" :visible.sync="moduleDialogFlag">
          <div class="row">
            <div class="col">
              <data-tables layout='table' :table-props="moduleSearchGrid.tableProps" :data='moduleSearchGrid.data' @selection-change="handleModuleSelectionChange">
                <el-table-column type="selection" width="60"></el-table-column>
                <el-table-column prop="module_id" :label="labels.module" ></el-table-column>
                <el-table-column prop="description" :label="labels.description" ></el-table-column>
              </data-tables>
            </div>
          </div>
          <div class="row">
            <div class="col mt-2">
              <div class='d-flex justify-content-end'>
                <a class="cs-btn purple sm" v-on:click="saveModule">{{labels.save}}</a>
                <a class="cs-btn purple sm" v-on:click="moduleDialogFlag = false">{{labels.close}}</a>
              </div>
            </div>
          </div>
        </el-dialog>
      </b-col>
    </b-row>
    </b-card>
  </div>
</template>

<script>
import {parse} from 'json2csv'

export default {
  name: 'APPLICATION',
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
        appId: '',
        description: '',
        url: '',
        createId: '',
        createDt: '',
        updateId: '',
        updateDt: '',
        authorityId: '',
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
        excelDownload: '',
        applicationOption: '',
        label: '',
        module: '',
        urlMessage: '',
        moduleDialogTitle: '',
        addModule: ''
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
          prop: ['appId'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'Id' },
          { prop: 'appId', label: 'Application' },
          { prop: 'description', label: 'Description' },
          { prop: 'url', label: 'Path' },
          { prop: 'createId', label: 'Creater' },
          { prop: 'createDt', label: 'Create Date' }
        ],
        columns: [ 'id', 'appId', 'description', 'url', 'createId', 'createDt' ],
        columnNames: [ 'id', 'appId', 'description', 'url', 'createId', 'createDt' ]
      },
      authorityGrid: {
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        filters: [{
          prop: [ 'authorityId', 'appId' ],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'authorityId', label: 'id.' },
          { prop: 'appId', label: 'appId' }
        ],
        columns: [ 'authorityId', 'appId' ],
        columnNames: [ 'authorityId', 'appId' ]
      },
      moduleGrid: {
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        filters: [{
          prop: ['description'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'module', label: 'Module' },
          { prop: 'description', label: 'Description' }
        ],
        columns: [ 'module', 'description' ],
        columnNames: [ 'module', 'description' ]
      },
      moduleSearchGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        currentData: {
        },
        filteredData: [],
        columns: ['module', 'description'],
        columnNames: ['module', 'description'],
        selection: []
      },
      appLabelGrid: {
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        currentData: {
        },
        data: [],
        filters: [{
          prop: [ 'name', 'description' ],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'name', label: 'Name' },
          { prop: 'description', label: 'Description' }
        ],
        columns: [ 'name', 'description' ],
        columnNames: [ 'name', 'description' ]
      },
      className: 'formdata sm float-right',
      classDes: 'formdata sm float-right',
      classUrl: 'formdata sm float-right',
      showName: false,
      nameText: '',
      saveShow: false,
      delShow: false,
      showDes: false,
      desText: '',
      showUrl: false,
      urlText: '',
      showAuthId: false,
      authIdText: '',
      authSelect: [],
      moduleDialogFlag: false
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      // button authority
      let authorities = this.$store.getters.getAuth
      for (let i = 0; i < authorities.length; i++) {
        if (authorities[i].authorityId === 'APPLICATION_SAVE') {
          this.saveShow = true
        }
        if (authorities[i].authorityId === 'APPLICATION_DELETE') {
          this.delShow = true
        }
      }
      this.$http.get(
        '/episode/api/system/application'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    init: function (id, appId) {
      this.className = 'formdata sm float-right'
      this.showName = false
      this.classDes = 'formdata sm float-right'
      this.showDes = false
      this.classUrl = 'formdata sm float-right'
      this.showUrl = false
      this.$http.get(
        '/episode/api/system/application/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get(
        '/episode/api/system/common/appoption?appId=' + appId
      ).then(res => {
        this.authorityGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get(
        '/episode/api/system/application/' + id + '/module'
      ).then(res => {
        this.moduleGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get(
        '/episode/api/system/application/' + id + '/applabel'
      ).then(res => {
        this.appLabelGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    changeTab: function (tab, event) {
      this.activeTab = tab.name
      if (tab.name === 'list') {
        this.listGrid.currentData = {}
        this.className = 'formdata sm float-right'
        this.showName = false
        this.showDes = false
        this.showUrl = false
      }
    },
    // Blur (name / description / url)
    blurName: function () {
      if (!this.listGrid.currentData.hasOwnProperty('id')) {
        this.$http.get(
          '/episode/api/system/application?appId=' + this.listGrid.currentData.appId
        ).then(res => {
          if (res.data.length > 0 && this.listGrid.currentData.appId !== undefined && this.listGrid.currentData.appId !== '') {
            this.listGrid.currentData.appId = ''
            this.className = 'formdata sm error float-right'
            this.showName = true
            this.nameText = this.labels.nameExists
          } else if (this.listGrid.currentData.appId === undefined || this.listGrid.currentData.appId === '') {
            this.className = 'formdata sm error float-right'
            this.showName = true
            this.nameText = this.labels.labelnameMessage
          } else if (this.listGrid.currentData.appId.length < 4 || this.listGrid.currentData.appId.length > 20) {
            this.className = 'formdata sm error float-right'
            this.showName = true
            this.nameText = this.labels.lengthMessageA
          } else {
            this.className = 'formdata sm good float-right'
            this.showName = false
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    blurDes: function () {
      if (this.listGrid.currentData.description === undefined || this.listGrid.currentData.description === '') {
        this.classDes = 'formdata sm error float-right'
        this.showDes = true
        this.desText = this.labels.desMessage
      } else {
        this.classDes = 'formdata sm good float-right'
        this.showDes = false
      }
    },
    blurUrl: function () {
      if (this.listGrid.currentData.url === undefined || this.listGrid.currentData.url === '') {
        this.classUrl = 'formdata sm error float-right'
        this.showUrl = true
        this.urlText = this.labels.urlMessage
      } else if (this.listGrid.currentData.url.length < 4 || this.listGrid.currentData.url.length > 50) {
        this.classUrl = 'formdata sm error float-right'
        this.showUrl = true
        this.urlText = this.labels.lengthMessage
      } else {
        this.classUrl = 'formdata sm good float-right'
        this.showUrl = false
      }
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
      this.authorityGrid.data = []
      this.appLabelGrid.data = []
      this.activeTab = 'detail'
      this.className = 'formdata sm float-right'
      this.showName = false
      this.classDes = 'formdata sm float-right'
      this.showDes = false
      this.classUrl = 'formdata sm float-right'
      this.showUrl = false
    },
    save: function () {
      if (this.showName === false && this.showDes === false && this.showUrl === false && this.listGrid.currentData.appId !== undefined && this.listGrid.currentData.description !== undefined && this.listGrid.currentData.url !== undefined) {
        if (this.listGrid.currentData.hasOwnProperty('id')) {
          if (this.listGrid.currentData.state === 'CHANGED') {
            this.$http.put('/episode/api/system/application/' + this.listGrid.currentData.id, this.listGrid.currentData).then(res => {
              this.listGrid.data.splice(this.listGrid.rowIndex, 1, res.data)
              this.listGrid.currentData.state = undefined
              this.$util.esmessage(this, 'check', this.labels.saveMessage)
            }).catch(error => {
              this.$util.esmessage(this, 'warn', this.labels.failMessage)
              console.log(error)
            })
          }
        } else {
          this.$http.post('/episode/api/system/application', this.listGrid.currentData).then(res => {
            this.listGrid.currentData = res.data
            this.listGrid.rowIndex = 0
            this.listGrid.data.splice(this.listGrid.rowIndex, 0, res.data)
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      } else if (this.listGrid.currentData.appId === undefined && this.listGrid.currentData.description === undefined && this.listGrid.currentData.url === undefined) {
        this.className = 'formdata sm error float-right'
        this.showName = true
        this.nameText = this.labels.labelnameMessage
        this.classDes = 'formdata sm error float-right'
        this.showDes = true
        this.desText = this.labels.desMessage
        this.classUrl = 'formdata sm error float-right'
        this.showUrl = true
        this.urlText = this.labels.urlMessage
      } else if (this.listGrid.currentData.appId === undefined || this.listGrid.currentData.appId === '') {
        this.className = 'formdata sm error float-right'
        this.showName = true
        this.nameText = this.labels.labelnameMessage
      } else if (this.listGrid.currentData.description === undefined || this.listGrid.currentData.description === '') {
        this.classDes = 'formdata sm error float-right'
        this.showDes = true
        this.desText = this.labels.desMessage
      } else if (this.listGrid.currentData.url === undefined || this.listGrid.currentData.url === '') {
        this.classUrl = 'formdata sm error float-right'
        this.showUrl = true
        this.urlText = this.labels.urlMessage
      }
    },
    del: function () {
      if (this.listGrid.currentData.id === undefined || this.listGrid.currentData.id === '') {
        this.activeTab = 'list'
      } else if (this.appLabelGrid.data.length > 0 || this.authorityGrid.data.length > 0 || this.moduleGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        this.$http.delete('/episode/api/system/application/' + this.listGrid.currentData.id).then(res => {
          this.listGrid.data.splice(this.listGrid.rowIndex, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.activeTab = 'list'
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },

    // Module (add / save / delete)
    addModule: function () {
      this.moduleDialogFlag = true
      this.$http.get('/episode/api/system/common/module').then(res => {
        this.moduleSearchGrid.data = []
        res.data.forEach(item => {
          let exists = this.moduleGrid.data.filter(function (appmodule) {
            return appmodule.module_id === item.module_id
          }).length
          if (exists === 0) {
            this.moduleSearchGrid.data.push(item)
          }
        })
      }).catch(error => {
        console.log(error)
      })
    },
    saveModule: function () {
      this.moduleDialogFlag = false
      let seletedItem = this.moduleSearchGrid.selection
      if (seletedItem.length > 0) {
        for (let i = 0; i < seletedItem.length; i++) {
          let item = seletedItem[i]
          item.appId = this.listGrid.currentData.appId
        }
        this.$http.post('/episode/api/system/application/module', seletedItem).then(res => {
          this.moduleGrid.data = this.moduleGrid.data.concat(res.data)
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
    delModule: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/system/application/module/' + row.id).then(res => {
          this.moduleGrid.data.splice(index, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.moduleGrid.data.splice(index, 1)
      }
    },

    // Label (add / save / delete)
    addLabel: function () {
      this.appLabelGrid.data.splice(0, 0, {
        appId: this.listGrid.currentData.appId, lang: 'ko-kr', state: 'ADDED', createId: this.$store.getters.getUser.username, updateId: this.$store.getters.getUser.username
      })
    },
    saveLabel: function () {
      this.isProcess = true
      if (this.listGrid.currentData.hasOwnProperty('id')) {
        let indexMap = {}
        let data = this.$refs.labelTable.tableData.filter((data, index) => {
          let result = (data.state === 'ADDED')
          if (result) {
            indexMap[data.name + '-' + data.lang] = index
          }
          return result
        })
        if (data.filter(data => (data.name === '' || data.name === undefined)).length > 0) {
          this.$util.esmessage(this, 'warn', this.labels.labelnameMessage)
          this.isProcess = false
          return
        }
        if (data.filter(data => (data.description === '' || data.description === undefined)).length > 0) {
          this.$util.esmessage(this, 'warn', this.labels.descriptionMessage)
          this.isProcess = false
          return
        }
        if (data.filter(data => (data.lang === '' || data.lang === undefined)).length > 0) {
          this.$util.esmessage(this, 'warn', this.labels.langcodeMessage)
          this.isProcess = false
          return
        }
        for (let i = 0; i < data.length; i++) {
          let label1 = data[i]
          if (this.appLabelGrid.data.filter(label2 => label1.name === label2.name && label1.lang === label2.lang).length > 1) {
            this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
            this.isProcess = false
            return
          }
        }
        this.$http.post('/episode/api/system/application/label', data).then(res => {
          res.data.forEach(label => {
            this.appLabelGrid.data.splice(indexMap[label.name + '-' + label.lang], 1, label)
          })
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
      this.isProcess = false
    },
    delLabel: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/system/application/label/' + row.id).then(res => {
          this.appLabelGrid.data.splice(index, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.appLabelGrid.data.splice(index, 1)
      }
    },

    // excel download
    exportFilter: function () {
      let lang = this.$store.getters.getUser.lang
      let fileName = 'Application'
      if (lang === 'ko-KR') {
        fileName = '어플리케이션'
      }
      this.csvExport(this.listGrid.filteredData, this.listGrid.columns, this.listGrid.columnNames, fileName)
    },
    csvExport: function (data, fields, fieldNames, fileName) {
      const result = parse(data, fields)

      let csvContent = 'data:text/csv;charset=GBK,\uFEFF' + result
      let encodedUri = encodeURI(csvContent)

      let link = document.createElement('a')
      link.setAttribute('href', encodedUri)
      link.setAttribute('download', `${(fileName || 'file')}.csv`)
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    },

    dateTime: function (date) {
      return this.$util.dateTime(date)
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
  created: function () {
    this.initialize()
  }
}
</script>
