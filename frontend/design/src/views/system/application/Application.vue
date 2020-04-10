<!--
 File Name  : Application.vue
 Description : 어플리케이션
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.06  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <b-row>
            <b-col sm="2" >
              <div>
                <b-button class="move-btn" v-show="(activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')" v-on:click="preMovePage('previous')">
                  <img src="../../../assets/img/icons/arrow-left@2x.png"/>
                </b-button>
                <b-button class="move-btn" v-show="(activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')" v-on:click="preMovePage('next')">
                  <img src="../../../assets/img/icons/arrow-right@2x.png"/>
                </b-button>
              </div>
            </b-col>
            <b-col sm='10'>
              <div class="comm-btn-box" role="group">
                <b-button variant="primary" class="px-3 sm" v-show="visibleDelete" v-on:click="del">{{labels.delete}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
              </div>
            </b-col>
          </b-row>
          <p class="grid-line"></p>
          <el-tabs v-model="activeTab" :before-leave="chgTab">
            <el-tab-pane :label="labels.list" name="list">
              <b-row>
                <div class='col-md-4' style="padding-top:10px;padding-right:5px">
                  <div class="search-component sm">
                    <input class="form-control formdata sm" style="width: 100%;" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder=labels.searchA />
                  </div>
                </div>
                <div>
                  <el-popover placement="right" width="500" trigger="click" v-if="false" v-model="visibleSearchDetailFlag">
                    <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
                      {{labels.searchDetail}}
                    </b-row>
                    <b-row>
                      <div class='form-group col-md-6'>
                        <label for="application_name">{{labels.name}}</label>
                        <input id="application_name" class="formdata sm float-right" v-model="listGrid.searchParamDetail.appId">
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="application_description">{{labels.description}}</label>
                        <input id="application_description" class="formdata sm float-right" v-model="listGrid.searchParamDetail.description"/>
                      </div>
                    </b-row>
                    <b-row>
                      <div class='form-group col-md-6'>
                        <label for="application_url">{{labels.url}}</label>
                        <input id="application_url" class="formdata sm float-right" v-model="listGrid.searchParamDetail.url"/>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="application_status">{{labels.status}}</label>
                        <el-select id="application_status" v-model="listGrid.searchParamDetail.status">
                          <el-option  value="" key="">SELECT</el-option>
                          <el-option  value="ACTIVE" key="ACTIVE">ACTIVE</el-option>
                          <el-option  value="INACTIVE" key="INACTIVE">INACTIVE</el-option>
                        </el-select>
                      </div>
                    </b-row>
                    <b-row>
                      <div class="col mt-2">
                        <div class='d-flex justify-content-end'>
                          <b-button variant="primary" class="px-3 sm" v-on:click="searchDetailReset" >{{labels.reset}}</b-button>
                          <b-button variant="primary" class="px-3 sm" v-on:click="searchDetail">{{labels.search}}</b-button>
                        </div>
                      </div>
                    </b-row>
                    <el-button slot="reference" :class='searchDetailBtnClass'></el-button>
                  </el-popover>
                </div>
                <div class='col'>
                  <div class="float-right mt-2" role="group">
                    <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="float:right;padding: 0; width:40px; height: 40px;">
                      <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                    </el-button>
                  </div>
                </div>
              </b-row>
              <b-row>
                <div class='col'>
                  <p class='grid-line'></p>
                  <data-tables style="width: 100%" layout='table, pagination' :table-props='listGrid.tableProps' :page-size.sync='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps'
                  :current-page.sync='listGrid.pageIndex'
                  v-on:row-click="handleRowClick"
                  v-on:filtered-data="handleFilteredData"
                  v-if="listGridFlag">
                    <el-table-column prop="id"          :label="labels.id"          width="200" :sortable="true"></el-table-column>
                    <el-table-column prop="appId"       :label="labels.appId"       width="200" ></el-table-column>
                    <el-table-column prop="description" :label="labels.description" width="*" ></el-table-column>
                    <el-table-column prop="url"         :label="labels.url"         width="250" ></el-table-column>
                    <el-table-column prop="createDt"    :label="labels.createDt"    width="200" ><template slot-scope="scope">{{$util.dateTimeWithTimeZone(scope.row.createDt)}}</template></el-table-column>
                    <el-table-column prop="createNm"    :label="labels.createId"    width="200" ></el-table-column>
                  </data-tables>
                </div>
              </b-row>
            </el-tab-pane>
            <!--detail-->
            <el-tab-pane :label="labels.detail" name="detail" v-if="(activeTab !== 'list')">
              <b-row>
                <div class='col'>
                  <el-form :model="listGrid.currentData"  ref="appForm" label-width="120px" >
                    <b-row>
                      <div class='form-group col-md-3'>
                        <label for="application_id">{{labels.id}}</label>
                        <input id="application_id" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="application_name">{{labels.name}}<span class="require-mark">*</span></label>
                        <input id="application_name" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.appId" :placeholder="labels.searchMessage" :disabled="disabledAppId" v-on:blur="chkValueAppId(listGrid.currentData.appId)">
                        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.nameMsg)" v-text="validMsg.nameMsg"></p>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="application_description">{{labels.description}}<span class="require-mark">*</span></label>
                        <input id="application_description" class="formdata sm float-right" style="width: 100%" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" />
                        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
                      </div>
                    </b-row>
                    <b-row>
                      <div class='form-group col-md-6'>
                        <label for="application_status">{{labels.status}}<span class="require-mark">*</span></label>
                        <el-select id="application_status" v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" placeholder="Please select a STATUS" style="width: 100%;" >
                          <el-option  value="ACTIVE" key="ACTIVE">ACTIVE</el-option>
                          <el-option  value="INACTIVE" key="INACTIVE">INACTIVE</el-option>
                        </el-select>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="application_url">{{labels.url}}<span class="require-mark">*</span></label>
                        <input id="application_url" class="formdata sm float-right" style="width: 100%" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.url" placeholder="PATH" />
                        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.urlMsg)" v-text="validMsg.urlMsg"></p>
                      </div>
                    </b-row>
                    <b-row>
                      <div class='form-group col-md-3'>
                        <label for="application_createId">{{labels.createId}}</label>
                        <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="application_createDt">{{labels.createDt}}</label>
                        <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="application_updateId">{{labels.updateId}}</label>
                        <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label>{{labels.updateDt}}</label>
                        <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                      </div>
                    </b-row>
                  </el-form>
                </div>
              </b-row>
              <b-row>
                <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <b-row>
                <!--AUTH-->
                <div class="col-md-6">
                  <b-row>
                    <div class="col mb-2 mt-2">
                      <a class="title-box">{{labels.applicationOption}}</a>
                    </div>
                  </b-row>
                  <b-row>
                    <div class="col">
                      <p class="grid-line"></p>
                      <data-tables ref="authTable" layout='table, pagination' :current-page.sync='authorityGrid.pageIndex' :table-props='authorityGrid.tableProps' v-bind:data='authorityGrid.data' v-bind:filters='authorityGrid.filters' v-bind:page-size='authorityGrid.pageProps.pageSize' v-bind:pagination-props='authorityGrid.pageProps' >
                        <el-table-column prop="appId" v-bind:label="labels.authorityId"></el-table-column>
                        <el-table-column prop="description" v-bind:label="labels.description"></el-table-column>
                      </data-tables>
                    </div>
                  </b-row>
                </div>
                <!--Apply Module-->
                <div class="col-md-6">
                  <b-row>
                    <div class="col mb-2 mt-2">
                      <a class="title-box">{{labels.module}}</a>
                    </div>
                    <div class="col">
                      <div class="float-right" role="group">
                        <b-button variant="primary" class="px-3 sm" v-on:click="addModule" v-show="visibleAddModule">{{labels.addModule}}</b-button>
                      </div>
                    </div>
                  </b-row>
                  <b-row>
                    <div class="col">
                      <p class="grid-line"></p>
                      <data-tables ref="moduleTable" layout='table, pagination' :current-page.sync='moduleGrid.pageIndex' :table-props='moduleGrid.tableProps' :data='moduleGrid.data' :filters='moduleGrid.filters' :page-size='moduleGrid.pageProps.pageSize' :pagination-props='moduleGrid.pageProps' >
                        <el-table-column prop="module_id" :label="labels.module"></el-table-column>
                        <el-table-column prop="description" :label="labels.description"></el-table-column>
                        <el-table-column align="right" width="80">
                          <template slot-scope="props">
                            <el-button  v-on:click="delModule(props.$index, props.row)" :title="labels.delete" v-show="visibleDeleteModule" style="padding: 0; width:30px; height: 30px;">
                              <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                            </el-button>
                          </template>
                        </el-table-column>
                      </data-tables>
                    </div>
                  </b-row>
                </div>
              </b-row>
            </el-tab-pane>
            <!-- label tab  -->
            <el-tab-pane :label="labels.label" name="label" v-if="(activeTab !== 'list')">
              <b-row>
                <div class='col'>
                  <el-form :model="listGrid.currentData"  ref="appForm" label-width="120px" >
                    <b-row>
                      <div class='form-group col-md-3'>
                        <label for="application_id">{{labels.id}}</label>
                        <input id="application_id" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.id" placeholder="Id" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="application_name">{{labels.name}}<span class="require-mark">*</span></label>
                        <input id="application_name" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.appId" :placeholder="labels.searchMessage" :disabled="disabledAppId" v-on:blur="chkValueAppId(listGrid.currentData.appId)">
                        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.nameMsg)" v-text="validMsg.nameMsg"></p>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="application_description">{{labels.description}}<span class="require-mark">*</span></label>
                        <input id="application_description" class="formdata sm float-right" style="width: 100%" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" />
                        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
                      </div>
                    </b-row>
                    <b-row>
                      <div class='form-group col-md-6'>
                        <label for="application_status">{{labels.status}}<span class="require-mark">*</span></label>
                        <el-select id="application_status" v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" placeholder="Please select a STATUS" style="width: 100%;" >
                          <el-option  value="ACTIVE" key="ACTIVE">ACTIVE</el-option>
                          <el-option  value="INACTIVE" key="INACTIVE">INACTIVE</el-option>
                        </el-select>
                      </div>
                      <div class='form-group col-md-6'>
                        <label for="application_url">{{labels.url}}<span class="require-mark">*</span></label>
                        <input id="application_url" class="formdata sm float-right" style="width: 100%" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.url" placeholder="PATH" />
                        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.urlMsg)" v-text="validMsg.urlMsg"></p>
                      </div>
                    </b-row>
                    <b-row>
                      <div class='form-group col-md-3'>
                        <label for="application_createId">{{labels.createId}}</label>
                        <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="application_createDt">{{labels.createDt}}</label>
                        <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                      </div>
                      <div class='form-group col-md-3'>
                        <label for="application_updateId">{{labels.updateId}}</label>
                        <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
                      </div>
                      <div class='form-group col-md-3'>
                        <label>{{labels.updateDt}}</label>
                        <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                      </div>
                    </b-row>
                  </el-form>
                </div>
              </b-row>
              <!-- label grid  -->
              <b-row>
                <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <b-row>
                <div class="col-md-12">
                  <b-row>
                    <div class="col" style="padding-top:18px; padding-left:20px;">
                      <div class="search-component sm">
                        <input class="form-control formdata sm" style="width: 100%;" v-model="appLabelGrid.searchParam" v-on:keyup.enter="enterSearchLabel" :placeholder=labels.searchA />
                      </div>
                    </div>
                    <div class="col">
                      <div class="float-right mt-3" role="group">
                        <b-button variant="primary" class="px-3 sm" v-on:click="addLabel"  v-show="visibleAddLabel">{{labels.new}}</b-button>
                      </div>
                    </div>
                  </b-row>
                  <b-row>
                    <div class="col">
                      <p class="grid-line"></p>
                      <data-tables ref="labelTable" layout='table, pagination' :current-page.sync='appLabelGrid.pageIndex' :table-props='appLabelGrid.tableProps' :data='appLabelGrid.data' :filters='appLabelGrid.filters' :page-size='appLabelGrid.pageProps.pageSize' :pagination-props='appLabelGrid.pageProps'>
                        <el-table-column prop="name" :label="labels.name" >
                          <template slot-scope="props">
                            <input class="form-control formdata sm" type="text" placeholder="Label name" v-model="props.row.name" :disabled="props.row.hasOwnProperty('id')" />
                          </template>
                        </el-table-column>
                        <el-table-column prop="description" :label="labels.description" >
                          <template slot-scope="props">
                            <input class="form-control formdata sm" type="text" placeholder="Description" v-model="props.row.description" v-on:change="changeValue(props.row)"/>
                          </template>
                        </el-table-column>
                        <el-table-column prop="lang" :label="labels.lang" >
                          <template slot-scope="props">
                            <el-select style="width: 100%" v-model="props.row.lang" v-on:change="changeValue(props.row)">
                              <el-option  value="en-us" key="en-us" >en-us</el-option>
                              <el-option  value="ko-kr" key="ko-kr">ko-kr</el-option>
                            </el-select>
                          </template>
                        </el-table-column>
                        <el-table-column align="right" width="80">
                          <template slot-scope="props">
                            <el-button  v-on:click="delLabel(props.$index, props.row)" :title="labels.delete" v-show="visibleDeleteLabel" style="padding: 0; width:30px; height: 30px;">
                              <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                            </el-button>
                          </template>
                        </el-table-column>
                      </data-tables>
                    </div>
                  </b-row>
                </div>
              </b-row>
            </el-tab-pane>
          </el-tabs>
          <!-- module Add Dialog  -->
          <el-dialog :title="labels.moduleDialogTitle" :visible.sync="moduleDialogFlag">
            <b-row>
              <div class="col">
                <data-tables layout='table' :table-props="moduleSearchGrid.tableProps" :data='moduleSearchGrid.data' @selection-change="handleModuleSelectionChange">
                  <el-table-column type="selection" width="60"></el-table-column>
                  <el-table-column prop="module_id" :label="labels.module" ></el-table-column>
                  <el-table-column prop="description" :label="labels.description" ></el-table-column>
                </data-tables>
              </div>
            </b-row>
            <b-row>
              <div class="col mt-2">
                <div class='d-flex justify-content-end'>
                  <b-button variant="primary" class="px-3 sm" v-on:click="saveModule" v-show="visibleSaveModule">{{labels.save}}</b-button>
                  <b-button variant="primary" class="px-3 sm" v-on:click="moduleDialogFlag = false">{{labels.close}}</b-button>
                </div>
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
  name: 'APPLICATION',
  data () {
    return {
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
        pageIndex: 1,
        filters: [{
          prop: ['appId', 'description', 'url', 'status'],
          value: ''
        }, {
          prop: ['appId'],
          value: ''
        }, {
          prop: ['description'],
          value: ''
        }, {
          prop: ['url'],
          value: ''
        }, {
          prop: ['status'],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'appId', label: 'Application' },
          { prop: 'description', label: 'Description' },
          { prop: 'url', label: 'Path' },
          { prop: 'createNm', label: 'Creater' },
          { prop: 'createDt', label: 'Create Date' }
        ],
        columns: [ 'id', 'appId', 'description', 'url', 'createId', 'createDt' ],
        columnNames: [ 'id', 'appId', 'description', 'url', 'createId', 'createDt' ],
        searchParam: '',
        searchParamDetail: {
          appId: '',
          description: '',
          url: '',
          status: ''
        }
      },
      authorityGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: false,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
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
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: false,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
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
        pageIndex: 1,
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
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: false,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
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
        columnNames: [ 'name', 'description' ],
        searchParam: ''
      },
      authorities: {
        APPLICATION_ADD: false,
        APPLICATION_SAVE: false,
        APPLICATION_DELETE: false,
        APPLICATION_MODULE_ADD: false,
        APPLICATION_MODULE_SAVE: false,
        APPLICATION_MODULE_DELETE: false,
        APPLICATION_LABEL_ADD: false,
        APPLICATION_LABEL_SAVE: false,
        APPLICATION_LABEL_DELETE: false
      },
      validMsg: {
        nameMsg: '',
        descriptionMsg: '',
        urlMsg: ''
      },
      listGridFlag: false,
      moduleDialogFlag: false,
      validAppLabelFlag: false,
      visibleSearchDetailFlag: false
    }
  },
  computed: {
    disabledAppId: function () {
      return this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAdd: function () {
      return this.authorities.APPLICATION_ADD && (this.activeTab === 'list')
    },
    visibleSave: function () {
      return this.authorities.APPLICATION_SAVE && (this.activeTab !== 'list')
    },
    visibleDelete: function () {
      return this.authorities.APPLICATION_DELETE && (this.activeTab !== 'list')
    },
    visibleAddModule: function () {
      return this.authorities.APPLICATION_MODULE_ADD && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleSaveModule: function () {
      return this.authorities.APPLICATION_MODULE_ADD && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleDeleteModule: function () {
      return this.authorities.APPLICATION_MODULE_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAddLabel: function () {
      return this.authorities.APPLICATION_LABEL_SAVE && (this.activeTab !== 'list')
    },
    visibleDeleteLabel: function () {
      return this.authorities.APPLICATION_LABEL_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    searchDetailBtnClass: function () {
      let flag = false
      for (let key in this.listGrid.searchParamDetail) {
        if (!this.$util.isNull(this.listGrid.searchParamDetail[key])) {
          flag = true
        }
      }
      return flag ? 'filter-button-on' : 'filter-button'
    }
  },
  watch: {
    'listGrid.currentData.appId': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.nameMsg = ''
      }
    },
    'listGrid.currentData.description': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.descriptionMsg = ''
      }
    },
    'listGrid.currentData.url': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.urlMsg = ''
      }
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.getListData()
      this.listGridFlag = true
    },
    getListData: function () {
      this.$http.get(
        '/episode/api/system/application'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      this.$http.get(
        '/episode/api/system/application/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
        this.$http.get(
          '/episode/api/system/common/appoption?appId=' + res.data.appId
        ).then(res => {
          this.authorityGrid.data = res.data
        }).catch(error => {
          console.log(error)
        })
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
      this.getLabelData(id)
    },
    getLabelData: function (id) {
      this.$http.get(
        '/episode/api/system/application/' + id + '/applabel'
      ).then(res => {
        this.appLabelGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    chgTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        this.appLabelGrid.searchParam = ''
        this.appLabelGrid.filters[0].value = ''
        if (this.listGrid.currentData.state === 'CHANGED' || (this.appLabelGrid.data.filter(data => (data.state === 'ADDED')).length > 0)) {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.activeTab = 'list'
              $this.listGrid.currentData = {}
              $this.appLabelGrid.data = []
              for (let key in $this.validMsg) {
                $this.validMsg[key] = ''
              }
            })
        } else {
          this.listGrid.currentData = {}
          this.appLabelGrid.data = []
          for (let key in this.validMsg) {
            this.validMsg[key] = ''
          }
        }
      }
      return rtnFlag
    },
    chkChgData: function () {
      let state = this.listGrid.currentData.state
      if (state === 'CHANGED' || state === 'ADDED') {}
    },
    chkValueAppId: function (val) {
      if (!this.listGrid.currentData.hasOwnProperty('id') && !this.$util.isNull(val)) {
        this.$http.get(
          '/episode/api/system/application?appId=' + this.listGrid.currentData.appId
        ).then(res => {
          if (res.data.length > 0) {
            this.validMsg.nameMsg = this.labels.nameExists
            this.listGrid.currentData.appId = ''
          } else {
            this.validMsg.nameMsg = ''
          }
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.validMsg.nameMsg = ''
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
      this.listGrid.currentData = {status: 'ACTIVE', state: 'ADDED'}
      this.authorityGrid.data = []
      this.moduleGrid.data = []
      this.appLabelGrid.data = []
      this.activeTab = 'detail'
    },
    isValidationCheck: function () {
      let rtnFlag = true
      if (!this.$util.isNull(this.validMsg.nameMsg)) {
        rtnFlag = false
      }
      this.validMsg.descriptionMsg = ''
      this.validMsg.urlMsg = ''
      if (this.$util.isNull(this.listGrid.currentData.appId)) {
        this.validMsg.nameMsg = this.labels.labelnameMessage
        rtnFlag = false
      }
      if (this.$util.isNull(this.listGrid.currentData.description)) {
        this.validMsg.descriptionMsg = this.labels.desMessage
        rtnFlag = false
      }
      if (this.$util.isNull(this.listGrid.currentData.url)) {
        this.validMsg.urlMsg = this.labels.urlMessage
        rtnFlag = false
      }
      return rtnFlag
    },
    save: function () {
      if (this.isValidationCheck()) {
        let appLabel = this.saveLabel()
        if (this.validAppLabelFlag) return
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/application/' + this.listGrid.currentData.id : '/episode/api/system/application'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || appLabel.length > 0) {
          this.listGrid.currentData.appLabel = appLabel
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.getListData()
            this.getRowData(res.data.id)
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }
    },
    del: function () {
      if (this.$util.isNull(this.listGrid.currentData.id)) {
        this.activeTab = 'list'
      } else if (this.appLabelGrid.data.length > 0 || this.moduleGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        this.$http.delete('/episode/api/system/application/' + this.listGrid.currentData.id).then(res => {
          this.getListData()
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.activeTab = 'list'
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
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
      let seletedItem = this.moduleSearchGrid.selection
      if (seletedItem.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      if (seletedItem.length > 0) {
        for (let i = 0; i < seletedItem.length; i++) {
          let item = seletedItem[i]
          item.appId = this.listGrid.currentData.appId
        }
        this.$http.post('/episode/api/system/application/module', seletedItem).then(res => {
          this.moduleGrid.data = this.moduleGrid.data.concat(res.data)
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.moduleDialogFlag = false
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
    addLabel: function () {
      this.appLabelGrid.data.splice(0, 0, {
        appId: this.listGrid.currentData.appId, lang: 'ko-kr', state: 'ADDED'
      })
    },
    saveLabel: function () {
      this.validAppLabelFlag = false
      let data = this.appLabelGrid.data.filter((data, index) => {
        return data.state === 'ADDED' || data.state === 'CHANGED'
      })
      if (data.filter(data => (this.$util.isNull(data.name))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.labelnameMessage)
        this.validAppLabelFlag = true
        this.activeTab = 'label'
        return []
      }
      if (data.filter(data => (this.$util.isNull(data.description))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.descriptionMessage)
        this.validAppLabelFlag = true
        this.activeTab = 'label'
        return []
      }
      if (data.filter(data => (this.$util.isNull(data.lang))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.langcodeMessage)
        this.validAppLabelFlag = true
        this.activeTab = 'label'
        return []
      }
      for (let i = 0; i < data.length; i++) {
        let label1 = data[i]
        if (this.appLabelGrid.data.filter(label2 => label1.name === label2.name && label1.lang === label2.lang).length > 1) {
          this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
          this.validAppLabelFlag = true
          this.activeTab = 'label'
          return []
        }
      }
      return data
    },
    delLabel: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/system/application/label/' + row.id).then(res => {
          this.getLabelData(this.listGrid.currentData.id)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.appLabelGrid.data.splice(index, 1)
      }
    },
    exportFilter: function () {
      let fileName = 'Application'
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'appId', label: this.labels.appId},
        {value: 'description', label: this.labels.description},
        {value: 'url', label: this.labels.url},
        {value: 'createNm', label: this.labels.createId},
        {value: 'createDt', label: this.labels.createDt}
      ]
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    },
    handleRowClick: function (row, column, event) {
      this.activeTab = 'detail'
      this.listGrid.rowIndex = this.listGrid.filteredData.indexOf(row)
      this.getRowData(row.id)
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    handleModuleSelectionChange: function (val) {
      this.moduleSearchGrid.selection = val
    },
    enterSearch: function () {
      this.listGrid.pageIndex = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    enterSearchLabel: function () {
      this.appLabelGrid.pageIndex = 1
      this.appLabelGrid.filters[0].value = this.appLabelGrid.searchParam
    },
    searchDetail: function () {
      this.listGrid.pageIndex = 1
      this.listGrid.filters[1].value = this.listGrid.searchParamDetail.appId
      this.listGrid.filters[2].value = this.listGrid.searchParamDetail.description
      this.listGrid.filters[3].value = this.listGrid.searchParamDetail.url
      this.listGrid.filters[4].value = this.listGrid.searchParamDetail.status
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.pageIndex = 1
      this.listGrid.searchParamDetail.appId = ''
      this.listGrid.searchParamDetail.description = ''
      this.listGrid.searchParamDetail.url = ''
      this.listGrid.searchParamDetail.status = ''
      this.searchDetail()
      this.visibleSearchDetailFlag = false
    },
    preMovePage: function (moveStr) {
      if (this.listGrid.currentData.state === 'CHANGED' || (this.appLabelGrid.data.filter(data => (data.state === 'ADDED')).length > 0)) {
        let $this = this
        this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
          function () {
            $this.save()
          },
          function () {
            $this.movePage(moveStr)
          }
        )
        return false
      } else {
        this.movePage(moveStr)
      }
    },
    movePage: function (moveStr) {
      this.appLabelGrid.searchParam = ''
      this.appLabelGrid.filters[0].value = ''
      let oldRowIndex = this.listGrid.rowIndex
      let oldPageIndex = this.listGrid.pageIndex
      if (moveStr === 'previous') {
        this.listGrid.rowIndex = this.listGrid.rowIndex - 1
      } else {
        this.listGrid.rowIndex = this.listGrid.rowIndex + 1
      }
      let gridRowIndex = this.listGrid.rowIndex - (this.listGrid.pageProps.pageSize * (this.listGrid.pageIndex - 1))
      if (gridRowIndex < 0) {
        this.listGrid.pageIndex = this.listGrid.pageIndex - 1
      } else if (this.listGrid.pageProps.pageSize - 1 < gridRowIndex) {
        this.listGrid.pageIndex = this.listGrid.pageIndex + 1
      }
      let data = this.listGrid.filteredData
      if (this.listGrid.pageIndex === 0 || (data.length - 1 < this.listGrid.rowIndex)) {
        this.listGrid.rowIndex = oldRowIndex
        this.listGrid.pageIndex = oldPageIndex
      }
      this.getRowData(data[this.listGrid.rowIndex].id)
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.authorityGrid.data = []
    this.moduleGrid.data = []
    this.moduleSearchGrid.data = []
    this.appLabelGrid.data = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
