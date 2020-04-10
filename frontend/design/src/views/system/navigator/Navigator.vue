<!--
 Class Name  : Navigator.vue
 Description : 메뉴
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
          <div class="comm-btn-box" role="group">
            <b-button variant="primary" class="px-3 sm" v-show="visibleDelete"  v-on:click="del">{{labels.delete}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="3">
          <Tree
            v-if="treeFlag"
            v-bind:tree="tree"
            ref="tree"
            v-on:clickNode="clickNode"
            v-on:clickAddBtn="clickAddBtn"
          />
        </b-col>
        <b-col sm="9">
          <el-tabs v-model="activeTab" :before-leave="chgTab">
            <!--LIST-->
            <el-tab-pane  name="list" :label='labels.list'>
              <b-row>
                <div class="col-md-6" style="padding-top:10px; padding-left:20px;">
                  <div class="search-component sm">
                    <input class="form-control formdata sm" style="width: 80%;"  v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder="labels.search" />
                  </div>
                </div>
                <div class='col-md-6'>
                  <div class="float-right mt-1" role="group">
                    <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                      <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                    </el-button>
                  </div>
                </div>
              </b-row>
              <b-row class="mt-2">
                <div class='col'>
                  <p class='grid-line'></p>
                  <data-tables v-if="listGridFlag" style="width: 100%" layout='table, pagination' :current-page.sync='listGrid.currentPage' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps' v-on:row-click="clickGridRow" v-on:filtered-data="handleFilteredData">
                    <el-table-column :prop='listGrid.titles[0].prop' :label='labels.id'               sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                    <el-table-column :prop='listGrid.titles[2].prop' :label='labels.navigatorId'      sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                    <el-table-column :prop='listGrid.titles[3].prop' :label='labels.description'      sortable='custom' ></el-table-column>
                    <el-table-column :prop='listGrid.titles[4].prop' :label='labels.hierarchypath'    sortable='custom' ></el-table-column>
                    <el-table-column :prop='listGrid.titles[5].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
                  </data-tables>
                </div>
              </b-row>
            </el-tab-pane>
            <!--DETAIL-->
            <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
              <b-row>
                <div class="form-group col-md-2">
                  <label for="id">{{labels.id}}</label>
                  <input id="id" :placeholder="'ID'" v-model="listGrid.currentData.id" style="width: 100%" class="formdata sm" autocomplete="off" disabled="disabled"/>
                </div>
                <div class="form-group col-md-2">
                  <label for="orgId">{{labels.orgId}}</label>
                  <el-select v-model="listGrid.currentData.orgId" style="width:100%" :placeholder="labels.selectData"  disabled="disabled">
                    <el-option v-for="org in orgSelect" :prop="org.orgId" :value="org.orgId" :key="org.orgId">
                      {{org.orgId}}
                    </el-option>
                  </el-select>
                </div>
                <div class="form-group col-md-2">
                  <label for="status">{{labels.status}}</label>
                  <el-select v-model="listGrid.currentData.status" style="width:100%" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
                    <el-option v-for="status in statusSelect" :prop="status.value" :value="status.value" :key="status.key">
                      {{status.value}}
                    </el-option>
                  </el-select>
                </div>
                <div class="form-group col-md-6">
                  <label for="hierarchypath">{{labels.hierarchypath}}</label>
                  <input id="hierarchypath" :placeholder="'PATH'" v-model="listGrid.currentData.hierarchypath" style="width: 100%" class="formdata sm" autocomplete="off" v-bind:disabled="true"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-2">
                  <label for="parent">{{labels.parent}}</label>
                  <div class="search-component sm">
                    <input id="parent" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.parent" style="width: 100%" class="formdata sm" autocomplete="off" readonly/>
                    <a class="search-button cursor-pointer" v-on:click="openParentDialog">search</a>
                  </div>
                </div>
                <div class="form-group col-md-3">
                  <label for="navigatorId">{{labels.navigatorId}}<span class="require-mark">*</span></label>
                  <input id="navigatorId" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.navigatorId" v-on:blur="getNavigatorIdChk" style="width: 100%" class="formdata sm" autocomplete="off" v-bind:disabled="listGrid.currentData.hasOwnProperty('id')" v-bind:maxlength="30"/>
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.navigatorIdMsg)" v-text="validMsg.navigatorIdMsg"></p>
                </div>
                <div class="form-group col-md-6">
                  <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
                  <input id="description" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.description" style="width: 100%" class="formdata sm" autocomplete="off" v-bind:maxlength="50" v-on:change="changeValue(listGrid.currentData)"/>
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
                </div>
                <div class="form-group col-md-1">
                  <label for="orderSeq">{{labels.order}}</label>
                  <input id="orderSeq" v-model="listGrid.currentData.orderSeq" style="width: 100%" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-12">
                  <label for="navigatoricon">{{labels.navigatoricon}}<span class="require-mark">*</span></label>
                  <i class="class-svg" v-html="listGrid.currentData.navigatoriconsvg"></i>
                  <input id="navigatoriconsvg" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.navigatoriconsvg" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.svgMsg)" v-text="validMsg.svgMsg"></p>
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
              <b-row class='mt-2'>
                <div class="col" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <!-- App -->
              <b-row class='mt-2'>
                <div class="col-md-12">
                  <b-row>
                    <div class="col" style="padding-top:20px; padding-left:20px;">
                      <a class="title-box">{{labels.app}}</a>
                    </div>
                  </b-row>
                  <b-row>
                    <div class="col" style="padding-top:18px; padding-left:20px;">
                      <div class="search-component sm">
                        <input class="form-control formdata sm" v-model="appListGrid.searchParam" v-on:keyup.enter="enterSearchApp" style="width: 100%;" :placeholder="labels.search" />
                      </div>
                    </div>
                    <div class="col">
                      <div class="float-right mt-3" role="group">
                        <b-button variant="primary" class="px-3 sm" v-show="visibleAddApp" v-on:click="openAppDialog">{{labels.add}}</b-button>
                      </div>
                    </div>
                  </b-row>
                  <b-row class='mt-2'>
                    <div class='col'>
                      <p class="grid-line"></p>
                      <data-tables layout='table, pagination' :current-page.sync='appListGrid.currentPage' :data='appListGrid.data' :filters='appListGrid.filters' :page-size='appListGrid.pageProps.pageSize' :pagination-props='appListGrid.pageProps'>
                        <el-table-column :prop='appListGrid.titles[0].prop' :label='labels.id'               sortable='custom' :width='appListGrid.titles[0].width'></el-table-column>
                        <el-table-column :prop='appListGrid.titles[1].prop' :label='labels.appId'            sortable='custom' :width='appListGrid.titles[1].width'></el-table-column>
                        <el-table-column :prop='appListGrid.titles[2].prop' :label='labels.description'      sortable='custom' ></el-table-column>
                        <el-table-column :prop='appListGrid.titles[3].prop' :label='labels.url'              sortable='custom' ></el-table-column>
                        <el-table-column align="right" width="80">
                          <template slot-scope="props">
                            <el-button v-show="visibleDeleteApp" v-on:click="delApp(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
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
        </b-col>
      </b-row>
      <!-- Parent Dialog -->
      <el-dialog :title="labels.parentDialogTitle" :visible.sync="showParentDialogFlag" width=270px>
        <b-row>
          <div class="col">
            <div class="treeDialog">
              <TreeParent
                v-bind:tree="treeParent"
                ref="treeParent"
              />
            </div>
          </div>
        </b-row>
        <b-row>
          <div class="col mt-2">
            <div class='d-flex justify-content-end'>
              <b-button variant="primary" class="px-3 sm" v-on:click="confirmParentDialog">{{labels.confirm}}</b-button>
              <b-button variant="primary" class="px-3 sm" v-on:click="cancleParentDialog">{{labels.cancle}}</b-button>
            </div>
          </div>
        </b-row>
      </el-dialog>
      <!-- App Dialog -->
      <el-dialog :title="labels.appDialogTitle" :visible.sync="showAppDialogFlag">
        <b-row>
          <div class='col'>
            <div class="search-component sm">
              <input ref="authSearch" type="text" class="formdata sm" style="width: 100%;"  v-model="appSearchGrid.filters[0].value" :placeholder="labels.search" />
            </div>
          </div>
        </b-row>
        <b-row>
          <div class="col">
            <data-tables layout='table, pagination' :table-props="appSearchGrid.tableProps" :page-size='appSearchGrid.pageProps.pageSize' :data='appSearchGrid.data' :filters='appSearchGrid.filters' :pagination-props='appSearchGrid.pageProps' @selection-change="selectAppGridRow">
              <el-table-column type="selection" width="60"></el-table-column>
              <el-table-column prop="appId" :label="labels.appId" :sortable="true"></el-table-column>
              <el-table-column prop="description" :label="labels.description" :sortable="true"></el-table-column>
              <el-table-column prop="url" :label="labels.hierarchypath" :sortable="true"></el-table-column>
            </data-tables>
          </div>
        </b-row>
        <b-row>
          <div class="col mt-2">
            <div class='d-flex justify-content-end'>
              <b-button variant="primary" class="px-3 sm" v-on:click="saveApp">{{labels.add}}</b-button>
              <b-button variant="primary" class="px-3 sm" v-on:click="showAppDialogFlag = false">{{labels.close}}</b-button>
            </div>
          </div>
        </b-row>
      </el-dialog>
    </b-card>
  </div>
</template>
<script>
import Tree from '@/views/common/TreeCom'
export default {
  name: 'NAVIGATOR',
  components: {
    'Tree': Tree,
    'TreeParent': Tree
  },
  data () {
    return {
      labels: {
        title: '',
        list: '',
        detail: '',
        id: '',
        orgId: '',
        parent: '',
        navigatorId: '',
        status: '',
        description: '',
        hierarchypath: ''
      },
      tree: {
        data: [],
        expandKeys: ['root'],
        selectData: {},
        showTreeFlag: true,
        showIconFlag: true,
        showCntFlag: false,
        showNodeIconFn: function (data) {
          return data.navigatoriconsvg
        },
        shwoAddIconFn: function (data) {
          if (data.type === 'MENU') {
            return true
          } else {
            return false
          }
        }
      },
      treeParent: {
        data: [],
        expandKeys: ['root'],
        selectData: {},
        showTreeFlag: true,
        showIconFlag: true,
        showCntFlag: false,
        showNodeIconFn: function (data) {
          return data.classiconsvg
        },
        shwoAddIconFn: function (data) {
          return false
        }
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 20, 30]
        },
        data: [],
        dataBak: [],
        filters: [{
          prop: ['id', 'orgId', 'parent', 'navigatorId', 'description', 'status'],
          value: ''
        }, {
          prop: 'treeData',
          value: '',
          filterFn: function (data, item) {
            let treeData = item.value
            return (data.hierarchypath.indexOf(treeData.hierarchypath + '/') > -1 || data.navigatorId === treeData.navigatorId || treeData.navigatorId === 'MENU')
          }
        }],
        filteredData: [],
        titles: [{
          prop: 'id',
          label: 'id.',
          width: '100px'
        }, {
          prop: 'orgId',
          label: 'orgId',
          width: '100px'
        }, {
          prop: 'navigatorId',
          label: 'navigatorId',
          width: '150px'
        }, {
          prop: 'description',
          label: 'description',
          width: '300x'
        }, {
          prop: 'hierarchypath',
          label: 'hierarchypath',
          width: '490px'
        }, {
          prop: 'status',
          label: 'status',
          width: '100px'
        }],
        columns: ['id', 'orgId', 'parent', 'navigatorId', 'description', 'status'],
        columnNames: ['id', 'orgId', 'parent', 'navigatorId', 'description', 'status'],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      appListGrid: {
        currentPage: 1,
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        currentData: {
        },
        data: [],
        filters: [{
          prop: [ 'navigatorId', 'description' ],
          value: ''
        }],
        filteredData: [],
        titles: [
          {prop: 'id', label: 'id', width: '200px'},
          {prop: 'navigatorId', label: 'appId', width: '200px'},
          {prop: 'description', label: 'description', width: '300px'},
          {prop: 'url', label: 'url', width: '400px'}
        ],
        columns: [
          'id',
          'navigatorId',
          'description',
          'hierarchypath'
        ],
        columnNames: [
          'id',
          'navigatorId',
          'description',
          'hierarchypath'
        ],
        searchParam: ''
      },
      appSearchGrid: {
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
          { prop: 'url', label: 'Path' }
        ],
        columns: ['id', 'appId', 'description', 'url'],
        columnNames: ['id', 'appId', 'description', 'url'],
        selection: []
      },
      // Auth
      authorities: {
        NAVIGATOR_ADD: false,
        NAVIGATOR_SAVE: false,
        NAVIGATOR_DELETE: false,
        NAVIGATOR_APP_ADD: false,
        NAVIGATOR_APP_DELETE: false
      },
      // Combo Data
      orgSelect: [],
      statusSelect: [],
      measureunitSelect: [],
      spectypeSelect: [],
      usewithTableSelect: [],
      ancestor: [],
      // Global Var
      orgId: '',
      lang: '',
      activeTab: 'list',
      // Global Flag
      showParentDialogFlag: false,
      showAppDialogFlag: false,
      showTreeMenu: false,
      listGridFlag: false,
      treeFlag: false,
      // Global Msg
      validMsg: {
        navigatorIdMsg: '',
        descriptionMsg: '',
        svgMsg: ''
      }
    }
  },
  watch: {
    activeTab: function (val) {
      this.showParentDialogFlag = false
      this.validMsg.navigatorIdMsg = ''
      this.validMsg.descriptionMsg = ''
    },
    'listGrid.currentData.id': function (val) {
      this.showParentDialogFlag = false
      this.validMsg.navigatorIdMsg = ''
      this.validMsg.descriptionMsg = ''
    },
    'listGrid.currentData.navigatorId': function (val) {
      if (!this.$util.isNull(val) && !this.listGrid.currentData.hasOwnProperty('id')) {
        this.listGrid.currentData.navigatorId = this.changeUpper(val)
        this.validMsg.navigatorIdMsg = ''
        this.changeValue(this.listGrid.currentData)
      }
    },
    'listGrid.currentData.description': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.descriptionMsg = ''
      }
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.NAVIGATOR_ADD && (this.activeTab === 'list')
    },
    visibleSave: function () {
      return this.authorities.NAVIGATOR_SAVE && (this.activeTab !== 'list')
    },
    visibleDelete: function () {
      return this.authorities.NAVIGATOR_DELETE && (this.activeTab !== 'list')
    },
    visibleAddApp: function () {
      return this.listGrid.currentData.hasOwnProperty('id') && this.listGrid.currentData.type === 'MENU' && this.authorities.NAVIGATOR_APP_ADD
    },
    visibleDeleteApp: function () {
      return this.listGrid.currentData.hasOwnProperty('id') && this.listGrid.currentData.type === 'MENU' && this.authorities.NAVIGATOR_APP_DELETE
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      let user = this.$store.getters.getUser
      this.lang = (user === null) ? 'en-us' : user.lang
      this.orgId = this.$store.getters.getUser.orgId
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'orgSelect', query: '/episode/api/system/common/organization'},
          {dataObj: 'statusSelect', domainId: 'CLASSSTATUS'}
        ]
      }
      this.listGridFlag = true
      this.treeFlag = true
      this.$util.getComboData(param)
      this.getTreeData()
      this.getListData()
    },
    // USER FN ////////////////////////////////////////////
    makeHierarchypath: function () {
      let parent = this.listGrid.currentData.parent
      let navigatorId = this.listGrid.currentData.navigatorId
      let hierarchypath = ''
      if (!this.$util.isNull(parent)) {
        let data = this.treeParent.selectData
        hierarchypath = data.hierarchypath
      }
      this.listGrid.currentData.hierarchypath = hierarchypath + '/' + navigatorId
    },
    isValidationCheck: function () {
      let rtnFlag = true
      let navigatorId = this.listGrid.currentData.navigatorId
      let description = this.listGrid.currentData.description
      let svg = this.listGrid.currentData.navigatoriconsvg
      if (this.$util.isNull(navigatorId)) {
        this.validMsg.navigatorIdMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(description)) {
        this.validMsg.descriptionMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(svg)) {
        this.validMsg.svgMsg = this.labels.inputData
        rtnFlag = false
      }
      return rtnFlag
    },
    changeUpper: function (val) {
      return val.toUpperCase()
    },
    expandNodes: function (nodes) {
      if (!this.$util.isNull(nodes)) {
        nodes.expand()
        this.expandNodes(nodes.parent)
      }
    },
    // TRNS FN ////////////////////////////////////////////
    getTreeData: function (id) {
      this.$http.get(
        '/episode/api/system/common/navitree?orgId=' + this.orgId + '&lang=' + this.lang
      ).then(res => {
        this.tree.data = res.data
        if (!this.$util.isNull(id)) {
          this.tree.expandKeys = [id]
          setTimeout(() => {
            this.$refs.tree.$refs.tree.setCurrentKey(id)
          }, 100)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function (id) {
      this.$http.get(
        '/episode/api/system/navigator?orgId=' + this.orgId + '&lang=' + this.lang
      ).then(res => {
        this.listGrid.data = res.data
        this.listGrid.dataBak = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      if (this.$util.isNull(id) || id === 'root') {
        return
      }
      this.$http.get(
        '/episode/api/system/navigator/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
      this.getAncestorData(id)
      this.getAppData(id)
    },
    getAncestorData: function (id) {
      this.$http.get(
        '/episode/api/system/navigator/' + id + '/ancestor'
      ).then(res => {
        this.ancestor = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getAppData: function (id) {
      this.$http.get(
        '/episode/api/system/navigator/' + id + '/app'
      ).then(res => {
        this.appListGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getNavigatorIdChk: function () {
      if (this.listGrid.currentData.navigatorId !== '') {
        this.$http.get(
          '/episode/api/system/navigator?orgId=' + this.listGrid.currentData.orgId + '&navigatorId=' + this.listGrid.currentData.navigatorId
        ).then(res => {
          if (res.data.length > 0) {
            this.listGrid.currentData.navigatorId = ''
            this.validMsg.navigatorIdMsg = this.labels.navigatorIdExist
          } else {
            this.makeHierarchypath()
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    save: function () {
      if (this.isValidationCheck()) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/navigator/' + this.listGrid.currentData.id : '/episode/api/system/navigator'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.getListData(res.data.id)
            this.getRowData(res.data.id)
            this.getTreeData(res.data.id)
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
      } else if (this.ancestor.length > 1 || this.appListGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        let $this = this
        this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
          this.$http.delete(
            '/episode/api/system/navigator/' + $this.listGrid.currentData.id
          ).then(res => {
            $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
            $this.activeTab = 'list'
            $this.listGrid.searchParam = ''
            $this.listGrid.filters[0].value = ''
            $this.listGrid.filters[1].value = ''
            $this.listGrid.currentData = {}
            $this.getListData()
          }).catch(error => {
            $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
            console.log(error)
          })
        })
      }
    },
    saveApp: function () {
      let dataList = []
      for (let idx in this.appSearchGrid.selection) {
        let data = {
          orgId: this.listGrid.currentData.orgId,
          parent: this.listGrid.currentData.navigatorId,
          navigatorId: this.appSearchGrid.selection[idx].appId,
          description: this.appSearchGrid.selection[idx].description,
          hierarchypath: this.listGrid.currentData.hierarchypath + '/' + this.appSearchGrid.selection[idx].appId,
          status: this.listGrid.currentData.status,
          type: 'APP',
          navigatoricon: 'icon-list',
          navigatoriconsvg: this.listGrid.currentData.navigatoriconsvg,
          orderSeq: 0
        }
        dataList.push(data)
      }
      if (dataList.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noselect)
        return
      }
      this.$http.post(
        '/episode/api/system/navigator/app'
        , dataList
      ).then(res => {
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.showAppDialogFlag = false
        this.getRowData(this.listGrid.currentData.id)
        this.getListData()
      }).catch(error => {
        console.log(error)
      })
    },
    delApp: function (index, row) {
      this.$http.delete('/episode/api/system/navigator/app/' + row.id).then(res => {
        this.getRowData(this.listGrid.currentData.id)
        this.getListData()
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    chgTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        this.listGrid.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.appListGrid.searchParam = ''
        this.appListGrid.filters[0].value = ''
        if (this.isChkChg()) {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.listGrid.currentData = {}
              $this.activeTab = 'list'
            })
        }
      }
      return rtnFlag
    },
    isChkChg: function () {
      return this.listGrid.currentData.state === 'ADDED' || this.listGrid.currentData.state === 'CHANGED'
    },
    exportFilter: function () {
      let fileName = 'navigator'
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'navigatorId', label: this.labels.navigatorId},
        {value: 'description', label: this.labels.description},
        {value: 'hierarchypath', label: this.labels.hierarchypath},
        {value: 'status', label: this.labels.status}
      ]
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    },
    clickNode: function (node) {
      if (this.activeTab !== 'list') {
        if (this.isChkChg()) {
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              if (!$this.$util.isNull(node)) {
                $this.listGrid.searchParam = ''
                $this.listGrid.filters[0].value = ''
                $this.listGrid.filters[1].value = node.data
              }
              $this.getRowData(node.data.id)
            }
          )
          return
        }
      }
      if (!this.$util.isNull(node)) {
        this.listGrid.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.listGrid.filters[1].value = node.data
      }
      if (node.text === 'CLASSSTRUCTURE') {
        return
      }
      this.getRowData(node.data.id)
    },
    clickAddBtn: function (parent) {
      if (this.activeTab !== 'list') {
        if (this.isChkChg()) {
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.addData(parent)
            }
          )
          return
        }
      }
      this.addData(parent)
    },
    addData: function (parent) {
      this.activeTab = 'detail'
      this.treeParent.selectData = parent
      this.listGrid.currentData = {
        orgId: 'KAI',
        parent: parent.navigatorId === 'MENU' ? '' : parent.navigatorId,
        status: 'ACTIVE',
        hierarchypath: '',
        type: 'MENU',
        navigatoricon: 'icon-list',
        orderSeq: 0
      }
      this.appListGrid.data = []
    },
    clickGridRow: function (row, column, event) {
      this.activeTab = 'detail'
      this.tree.expandKeys = [row.id]
      this.$refs.tree.$refs.tree.setCurrentKey(row.id)
      this.getRowData(row.id)
    },
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    },
    add: function () {
      this.activeTab = 'detail'
      this.listGrid.currentData = {
        orgId: 'KAI',
        parent: '',
        navigatorId: '',
        status: 'ACTIVE',
        hierarchypath: '',
        type: 'MENU',
        navigatoricon: 'icon-list',
        orderSeq: 0
      }
      this.appListGrid.data = []
    },
    openParentDialog: function () {
      this.treeParent.data = this.tree.data
      this.showParentDialogFlag = true
    },
    confirmParentDialog: function () {
      let treedata = this.$refs.treeParent.$refs.tree.getCurrentNode().data
      if (treedata !== undefined) {
        this.listGrid.currentData.parent = (treedata.navigatorId === 'MENU' ? '' : treedata.navigatorId)
        this.changeValue(this.listGrid.currentData)
        let orgData = this.listGrid.data.filter((listGridData, index) => {
          return (listGridData.id === this.listGrid.currentData.id)
        })
        if (this.listGrid.currentData.hasOwnProperty('id')) {
          if (treedata.hierarchypath.indexOf(orgData[0].hierarchypath) > -1) {
            this.$util.esmessage(this, 'warn', this.labels.notChildrenSelect)
            return
          }
        }
        this.showParentDialogFlag = false
        this.makeHierarchypath()
      }
    },
    cancleParentDialog: function () {
      this.showParentDialogFlag = false
      this.treeParent.data = []
      this.listGrid.currentData.parent = null
    },
    openAppDialog: function () {
      this.showAppDialogFlag = true
      this.$http.get(
        '/episode/api/system/navigator/appSearch'
      ).then(res => {
        this.appSearchGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    selectAppGridRow: function (val) {
      this.appSearchGrid.selection = val
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    enterSearchApp: function () {
      this.appListGrid.currentPage = 1
      this.appListGrid.filters[0].value = this.appListGrid.searchParam
    }
  },
  created: function () {
    this.initialize()
  },
  mounted: function () {
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.appListGrid.data = []
    this.appSearchGrid.data = []
    this.tree.data = []
    this.treeParent.data = []
    this.orgSelect = []
    this.statusSelect = []
    this.ancestor = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.treeFlag = false
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
