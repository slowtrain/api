<!--
 File Name  : Usergroup.vue
 Description : 유저그룹
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.06  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <b-row>
            <b-col sm="2" >
              <div>
                <b-button class="move-btn" v-show="(activeTab !== 'list') && listGrid.currentData.hasOwnProperty('id')" v-on:click="moveChk('previous')">
                  <img src="../../../assets/img/icons/arrow-left@2x.png"/>
                </b-button>
                <b-button class="move-btn" v-show="(activeTab !== 'list') && listGrid.currentData.hasOwnProperty('id')" v-on:click="moveChk('next')">
                  <img src="../../../assets/img/icons/arrow-right@2x.png"/>
                </b-button>
              </div>
            </b-col>
            <b-col sm="10">
              <div class="comm-btn-box" role="group">
                <b-button variant="primary" class="px-3 sm" v-show="visibleDelete" v-on:click="del">{{labels.delete}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
              </div>
            </b-col>
          </b-row>

    <el-tabs v-model="activeTab" :before-leave="chgTab">
      <!-- list -->
      <el-tab-pane  name="list" :label='labels.list'>
        <b-row>
            <div class='col-md-4'>
              <div class="search-component sm">
                <input class="form-control formdata sm" type="text" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder="labels.search" />
              </div>
            </div>
             <div class='col'>
            <div class='d-flex justify-content-end'>
              <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                <img src="../../../assets/img/icons/icon_search_download.png" style="width: 60%; "/>
              </el-button>
            </div>
          </div>
        </b-row>

        <b-row>
          <div class='col'>
            <p class='grid-line'></p>
              <data-tables-server
                ref="grid"
                :table-props='listGrid.tableProps'
                :data="listGrid.data"
                :filters="listGrid.filters"
                :total="listGrid.total"
                v-on:query-change="getUserGroup"
                v-on:row-click="selectData"
                :page-size='listGrid.pageProps.pageSize'
                :pagination-props="listGrid.pageProps"  :current-page.sync='listGrid.pageIndex'>
              <el-table-column prop="id" :label="labels.id" width="100" ></el-table-column>
              <el-table-column prop="orgId" :label="labels.orgId" :sortable="true" width="200"></el-table-column>
              <el-table-column prop="usergroupId" :label="labels.usergroupId" :sortable="true" width="250"></el-table-column>
              <el-table-column prop="description" :label="labels.description" :sortable="true"></el-table-column>
              <el-table-column prop="status" :label="labels.status" :sortable="true" width="200"></el-table-column>
              <el-table-column prop="createDt" :label="labels.createDt" width="250"><template slot-scope="scope">{{dateTime(scope.row.createDt)}}</template></el-table-column>
              <el-table-column prop="createNm" :label="labels.createId" width="150"></el-table-column>
              </data-tables-server>
          </div>
        </b-row>
      </el-tab-pane>

      <!-- detail -->
      <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
        <b-row>
          <div class='col-md-12'>
            <el-form :model="listGrid.currentData" label-width="120px" >
              <b-row>
                <div class="form-group col-md-1">
                  <label for="usergroup_id">{{labels.id}}</label>
                  <input id="usergroup_id" type="text" v-model="listGrid.currentData.id"  class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-2">
                  <label for="usergroup_orgId">{{labels.orgId}}<span class="require-mark">*</span></label>
                  <input id="usergroup_orgId" type="text" v-model="listGrid.currentData.orgId" class="formdata sm float-right" v-show="(listGrid.currentData.id !== undefined)" disabled="disabled"/>
                  <el-select id="orgId" v-model="listGrid.currentData.orgId" style="width:100%" v-show="(listGrid.currentData.id === undefined)">
                    <el-option v-for="org in orgSelect" :prop="org.orgId" :value="org.orgId" :key="org.orgId"></el-option>
                  </el-select>
                </div>
                <div class="form-group col-md-3">
                  <label for="usergroup_groupId">{{labels.usergroupId}}<span class="require-mark">*</span></label>
                  <input id="usergroup_groupId" class="formdata sm float-right" v-on:blur="blurUsergroupId" v-on:change="changeValue(listGrid.currentData)" type="text" v-model="listGrid.currentData.usergroupId" style="width: 100%;" :disabled="listGrid.currentData.id !== undefined">
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.usergroupIdMsg)" v-text="validMsg.usergroupIdMsg"></p>
                </div>
                <div class="form-group col-md-3">
                  <label for="usergroup_description">{{labels.description}}<span class="require-mark">*</span></label>
                  <input id="usergroup_description" class="formdata sm float-right" v-on:blur="blurDescription" v-on:change="changeValue(listGrid.currentData)" type="text" v-model="listGrid.currentData.description" maxlength="20">
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
                </div>
                  <div class="form-group col-md-3">
                    <label>{{labels.status}}<span class="require-mark">*</span></label>
                    <el-select id="status" v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" style="width:100%">
                      <el-option v-for="status in userGroupStatusSelect" :prop="status.value" :value="status.value" :key="status.key">
                        {{ status.value }}
                      </el-option>
                    </el-select>
                  </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-3">
                  <label for="usergroup_updateId">{{labels.createId}}</label>
                  <input id="usergroup_updateId" type="text" v-model="listGrid.currentData.createNm" placeholder="CREATER" class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-3">
                  <label for="usergroup_updateDt">{{labels.createDt}}</label>
                  <input id="usergroup_updateDt" type="text" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-3">
                  <label for="usergroup_updateId">{{labels.updateId}}</label>
                  <input id="usergroup_updateId" type="text" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-3">
                  <label for="usergroup_updateDt">{{labels.updateDt}}</label>
                  <input id="usergroup_updateDt" type="text" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" class="formdata sm float-right" disabled="disabled">
                </div>
              </b-row>
            </el-form>
          </div>
        </b-row>
        <div class='row mt-2'>
          <div class="col" style="border: 0.5px solid #c8d3df;"></div>
        </div>
        <b-row>
          <div class="col" style="padding-top:15px;">
            <a class="title-box">{{labels.user}}</a>
          </div>
        </b-row>
        <div class="row mt-4">
          <div class='col-md-4'>
            <div class="search-component sm">
              <input id="searchGroupUser" type="text" class="form-control formdata sm" v-model="userGrid.searchParam" v-on:keyup.enter="userSearch" :placeholder="labels.search" />
            </div>
          </div>
          <div class='col-md-6'></div>
          <div class='col-md-2'>
            <div class='d-flex justify-content-end'>
              <b-button variant="primary" class="px-3 sm" v-show="visibleAddGroupUser && userFlag" v-on:click="openModelGroupUser">{{labels.multipleAdd}}</b-button>
            </div>
          </div>
        </div>

        <div class="row mt-1">
          <div class="col">
            <p class='grid-line'></p>
            <data-tables id="userGrid" layout='table, pagination' :current-page.sync='userGrid.pageIndex' :table-props="userGrid.tableProps" :page-size='userGrid.pageProps.pageSize' :data='userGrid.data' :filters='userGrid.filters' :pagination-props='userGrid.pageProps'>
              <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column>
              <el-table-column prop="username" :label="labels.username" :sortable="true" width="180px"></el-table-column>
              <el-table-column prop="firstname" :label="labels.firstname" :sortable="true" width="180px"></el-table-column>
              <el-table-column prop="email" :label="labels.email" :sortable="true"></el-table-column>
              <el-table-column prop="tel" :label="labels.tel" :sortable="true" width="200px"></el-table-column>
              <el-table-column prop="status" :label="labels.status" :sortable="true" width="100px"></el-table-column>
              <el-table-column align="right" width="80">
                <template slot-scope="props">
                  <el-button  v-on:click="deleteUser(props.row)" v-show="visibleDeleteUser" style="padding: 0; width:30px; height: 30px;">
                    <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>

    <!-- Group user Modlue -->
    <el-dialog :title="labels.userDialogTitle" :visible.sync="userDialogFlag">
      <b-row>
        <div class='col'>
          <div class="search-component sm">
            <input id="searchUser" class="form-control formdata sm" v-model="userSearchGrid.searchParam" v-on:keyup.enter="userAddSearch" :placeholder="labels.search" />
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="col">
          <p class='grid-line'></p>
          <data-tables layout='table, pagination' :current-page.sync='userSearchGrid.pageIndex' :table-props="userSearchGrid.tableProps" :page-size='userSearchGrid.pageProps.pageSize' :data='userSearchGrid.data' :filters='userSearchGrid.filters' :pagination-props='userSearchGrid.pageProps' @selection-change="handleUserSelectionChange">
            <el-table-column type="selection" width="60"></el-table-column>
            <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column>
            <el-table-column prop="username" :label="labels.username" :sortable="true" width="150px"></el-table-column>
            <el-table-column prop="firstname" :label="labels.firstname" :sortable="true" width="120px"></el-table-column>
            <el-table-column prop="email" :label="labels.email" :sortable="true" ></el-table-column>
            <el-table-column prop="orgId" :label="labels.orgId" :sortable="true" width="120px"></el-table-column>
            <el-table-column prop="status" :label="labels.status" :sortable="true" width="100px"></el-table-column>
          </data-tables>
        </div>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button class='px-3 sm' variant="primary" v-on:click="addGroupUser">{{labels.add}}</b-button>
            <b-button class='px-3 sm' variant="primary" v-on:click="userDialogFlag = false">{{labels.close}}</b-button>
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
  name: 'USERGROUP',
  mounted () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.userGrid.data = []
    this.userSearchGrid.data = []
    this.listGrid = {}
    this.userGrid = {}
    this.userSearchGrid = {}
    this.lables = {}
    this.$nextTick(function () {
      this.listVisiable = false
    })
  },
  data () {
    return {
      listVisiable: true,
      userFlag: true,
      labels: {
        add: '',
        title: ' ',
        user: '',
        detail: '',
        id: '',
        orgId: '',
        usergroupId: '',
        description: '',
        createDt: '',
        createId: '',
        updateDt: '',
        updateId: '',
        username: '',
        firstname: '',
        lastname: '',
        status: '',
        email: '',
        tel: '',
        search: '',
        authorityId: ' ',
        appId: ' ',
        userDialogTitle: '',
        multipleAdd: '',
        new: '',
        close: '',
        save: '',
        saveMessage: '',
        deleteMessage: '',
        delete: '',
        descriptionMessage: '',
        groupExist: '',
        groupMessage: '',
        lengthMessage: '',
        list: '',
        childUserMessage: '',
        excelDownload: '',
        reset: '',
        chkChgDataMsg: '',
        selectNullMsg: ''
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },

        filters: [{
          prop: ['usergroupId', 'description'],
          value: ''
        }, {
          prop: ['usergroupId'],
          value: ''
        }, {
          prop: ['description'],
          value: ''
        }, {
          prop: ['status'],
          value: ''
        }],
        pageIndex: 1,
        rowIndex: -1,
        filteredData: [],
        data: [],
        titles: [
          {prop: 'id', label: 'id', width: '70px'},
          {prop: 'orgId', label: 'orgId', width: '200px'},
          {prop: 'usergroupId', label: 'usergroupId', width: '200px'},
          {prop: 'description', label: 'description', width: '230px'},
          {prop: 'status', label: 'status', width: '200px'},
          {prop: 'createDt', label: 'createDt', width: '250px'},
          {prop: 'createId', label: 'createId', width: '150px'},
          {prop: 'createNm', label: 'createId', width: '150px'},
          {prop: 'updateDt', label: 'updateDt', width: '250px'},
          {prop: 'updateId', label: 'updateId', width: '150px'},
          {prop: 'updateNm', label: 'updateId', width: '150px'}
        ],
        columns: ['id', 'orgId', 'usergroupId', 'description', 'status', 'createDt', 'createId', 'updateDt', 'updateId'],
        columnNames: ['id', 'orgId', 'usergroupId', 'description', 'status', 'createDt', 'createId', 'updateDt', 'updateId'],
        searchParam: '',
        total: 0,
        srchCnd: null,
        currentData: {
        }
      },
      userGrid: {
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
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        filters: [{
          prop: ['username', 'firstname', 'email', 'tel'],
          value: ''
        }],
        filteredData: [],
        columns: ['username', 'firstname', 'lastname', 'status'],
        columnNames: ['username', 'firstname', 'lastname', 'status'],
        searchParam: '',
        selection: []
      },
      userSearchGrid: {
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
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },
        filters: [{
          prop: ['username', 'firstname', 'email'],
          value: ''
        }],
        filteredData: [],
        columns: ['username', 'firstname', 'lastname', 'status'],
        columnNames: ['username', 'firstname', 'lastname', 'status'],
        searchParam: '',
        selection: []
      },
      userSelect: [],
      orgSelect: [],
      userDialogFlag: false,
      activeTab: 'list',
      userGroupStatusSelect: [],
      authorities: {
        USERGROUP_SAVE: false,
        USERGROUP_DELETE: false
      },
      validMsg: {
        usergroupIdMsg: '',
        descriptionMsg: ''
      }
    }
  },
  computed: {
    visibleDelete: function () {
      return this.authorities.USERGROUP_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleSave: function () {
      return this.authorities.USERGROUP_SAVE && (this.activeTab !== 'list')
    },
    visibleAdd: function () {
      return this.authorities.USERGROUP_SAVE && (this.activeTab === 'list')
    },
    visibleAddGroupUser: function () {
      return this.authorities.USERGROUP_SAVE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleDeleteUser: function () {
      return this.authorities.USERGROUP_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
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
          {dataObj: 'userGroupStatusSelect', domainId: 'USERGROUPSTATUS'}
        ]
      }
      this.$util.getComboData(param)
      // this.getUserGroup()
      this.$http.get('/episode/api/system/common/organization').then(res => {
        this.orgSelect = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getUserGroup: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/system/usergroup',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    setData: function (data) {
      this.setCurrentData(data.id)
      this.getGroupUser(data.id)
    },
    setCurrentData: function (id) {
      this.$http.get('/episode/api/system/usergroup/' + id).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getGroupUser: function (id) {
      this.$http.get('/episode/api/system/usergroup/' + id + '/user/').then(res => {
        this.userGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    // Blur ( usergroupId / description)
    blurUsergroupId: function () {
      let usergroupId = this.listGrid.currentData.usergroupId
      if (this.$util.isNull(usergroupId)) {
        this.validMsg.usergroupIdMsg = this.labels.groupMessage
        return null
      }
      if (this.$util.lengthChk(usergroupId.length, 4, 20)) {
        this.validMsg.usergroupIdMsg = this.labels.lengthMessage
        return null
      }
      this.$http.get('/episode/api/system/usergroup/userGroupIdChk?usergroupId=' + usergroupId).then(res => {
        if (res.data.length > 0) {
          usergroupId = ''
          this.validMsg.usergroupIdMsg = this.labels.groupExist
        } else {
          this.validMsg.usergroupIdMsg = ''
        }
      })
    },
    blurDescription: function () {
      let description = this.listGrid.currentData.description
      if (this.$util.isNull(description)) {
        this.validMsg.descriptionMsg = this.labels.descriptionMessage
      } else if (this.$util.lengthChk(description.length, 4, 20)) {
        this.validMsg.descriptionMsg = this.labels.lengthMessage
      } else {
        this.validMsg.descriptionMsg = ''
      }
    },
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.usergroupId = data.usergroupId.toUpperCase()
        data.state = 'ADDED'
      }
    },
    // Usergroup ( add / save / delete)
    add: function () {
      // this.listGrid.currentData = {status: 'ACTIVE', state: 'ADDED', orgId: this.orgSelect[0].orgId}
      this.listGrid.currentData = {status: 'ACTIVE', orgId: this.orgSelect[0].orgId}
      this.userGrid.data = []
      this.activeTab = 'detail'
      this.userFlag = false
    },
    isValidationCheck: function () {
      let rtnFlag = true
      if (!this.$util.isNull(this.validMsg.usergroupIdMsg) || !this.$util.isNull(this.validMsg.descriptionMsg)) {
        rtnFlag = false
      }
      if (this.$util.isNull(this.listGrid.currentData.usergroupId)) {
        this.validMsg.usergroupIdMsg = this.labels.groupMessage
        rtnFlag = false
      }
      if (this.$util.isNull(this.listGrid.currentData.description)) {
        this.validMsg.descriptionMsg = this.labels.descriptionMessage
        rtnFlag = false
      }
      return rtnFlag
    },
    save: function () {
      if (this.isValidationCheck()) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = '/episode/api/system/usergroup'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.setData(res.data)
            this.getUserGroup()
            this.userFlag = true
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }
    },
    del: function () {
      this.$http.get('/episode/api/system/usergroup/' + this.listGrid.currentData.id + '/child/').then(res => {
        if (res.data.groupuser > 0) {
          this.$util.esmessage(this, 'warn', this.labels.childUserMessage)
          return null
        }
        this.$http.delete('/episode/api/system/usergroup/' + this.listGrid.currentData.id + '/' + this.listGrid.currentData.usergroupId).then(() => {
          this.getUserGroup()
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.activeTab = 'list'
          this.listGrid.currentData = {}
        }).catch(error => {
          console.log(error)
        })
      })
    },

    // group user dialog ( open / add / save / delete)
    openModelGroupUser: function () {
      this.userDialogFlag = true
      this.userSearchGrid.searchParam = ''
      this.userSearchGrid.filters[0].value = ''
      this.$http.get(
        '/episode/api/system/usergroup/user/orglist?orgId=' + this.listGrid.currentData.orgId + '&usernameOrg=' + this.listGrid.currentData.usergroupId
      ).then(res => {
        this.userSearchGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    addGroupUser: function () {
      this.saveUser(this.userSelect)
    },
    saveUser: function (rows) {
      if (rows.length !== 0) {
        let params = {usergroupId: this.listGrid.currentData.usergroupId, rows: rows}
        this.$http.post(
          '/episode/api/system/usergroup/user'
          , params
        ).then(() => {
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.getGroupUser(this.listGrid.currentData.id)
          this.userDialogFlag = false
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
      }
    },
    deleteUser: function (row) {
      this.$http.delete(
        '/episode/api/system/usergroup/user/' + row.groupUserId
      ).then(() => {
        this.getGroupUser(this.listGrid.currentData.id)
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
      }).catch(error => {
        console.log(error)
      })
    },

    // excel download
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'id'
      let sort = 'ascending'
      let lang = this.$store.getters.getUser.lang
      if (srchCnd != null && srchCnd !== undefined && srchCnd !== 'undefined') {
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
      }
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/system/usergroup',
        params
      ).then(res => {
        let fileName = 'UserGroup'
        let fields = [
          {value: 'id', label: this.labels.id},
          {value: 'orgId', label: this.labels.orgId},
          {value: 'usergroupId', label: this.labels.usergroupId},
          {value: 'description', label: this.labels.description},
          {value: 'createDt', label: this.labels.createDt},
          {value: 'createId', label: this.labels.createId}
        ]
        if (lang === 'ko-kr') {
          fileName = this.labels.title
        }
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    dateTime: function (date) {
      return this.$util.dateTimeWithTimeZone(date)
    },
    // tab
    chgTab: function (activeTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.activeTab = 'list'
              $this.listGrid.currentData = {}
              $this.listGrid.searchParam = ''
              $this.userGrid.searchParam = ''
              $this.listGrid.filters[0].value = ''
              $this.userGrid.filters[0].value = ''
              for (let key in $this.validMsg) {
                $this.validMsg[key] = ''
                $this.userFlag = true
              }
            })
        } else {
          this.listGrid.currentData = {}
          this.listGrid.searchParam = ''
          this.userGrid.searchParam = ''
          this.listGrid.filters[0].value = ''
          this.userGrid.filters[0].value = ''
          for (let key in this.validMsg) {
            this.validMsg[key] = ''
            this.userFlag = true
          }
        }
      }
      return rtnFlag
    },
    // Grid ( listGrid / userSearchGrid)
    selectData: function (currentRow) {
      // this.listGrid.rowIndex = this.listGrid.filteredData.indexOf(currentRow)
      this.listGrid.rowIndex = this.listGrid.data.indexOf(currentRow)
      this.setCurrentData(currentRow.id)
      this.getGroupUser(currentRow.id)
      this.activeTab = 'detail'
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    handleUserSelectionChange: function (val) {
      this.userSelect = val
    },
    changeUpper: function (val) {
      this.listGrid.currentData.usergroupId = val.toUpperCase()
    },
    enterSearch: function () {
      this.listGrid.pageIndex = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    userSearch: function () {
      this.userGrid.pageIndex = 1
      this.userGrid.filters[0].value = this.userGrid.searchParam
    },
    userAddSearch: function () {
      this.userSearchGrid.pageIndex = 1
      this.userSearchGrid.filters[0].value = this.userSearchGrid.searchParam
    },
    spInit: function () {
      this.listGrid.searchParam = ''
    },
    moveChk: function (moveStr) {
      if (this.listGrid.currentData.state === 'CHANGED') {
        let $this = this
        this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
          function () {
            $this.save()
          },
          function () {
            $this.userGrid.searchParam = ''
            $this.userGrid.filters[0].value = ''
            $this.movePage(moveStr)
          })
      } else {
        this.userGrid.searchParam = ''
        this.userGrid.filters[0].value = ''
        this.movePage(moveStr)
      }
    },
    movePage: function (moveStr) {
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      let srchCnd = this.listGrid.srchCnd
      let oldPage = srchCnd.page
      let oldRowIndex = this.listGrid.rowIndex
      if (moveStr === 'previous') {
        this.listGrid.rowIndex = this.listGrid.rowIndex - 1
      } else {
        this.listGrid.rowIndex = this.listGrid.rowIndex + 1
      }
      if (this.listGrid.rowIndex < 0) {
        this.listGrid.rowIndex = srchCnd.pageSize - 1
        srchCnd.page = srchCnd.page - 1
      } else if (srchCnd.pageSize - 1 < this.listGrid.rowIndex) {
        this.listGrid.rowIndex = 0
        srchCnd.page = srchCnd.page + 1
      }
      if (srchCnd.page === 0 || oldPage === srchCnd.page || Math.ceil((this.listGrid.total / srchCnd.pageSize)) < srchCnd.page) {
        if (srchCnd.page === 0 || (this.listGrid.data.length - 1 < this.listGrid.rowIndex)) {
          this.listGrid.rowIndex = oldRowIndex
        }
        srchCnd.page = oldPage
        this.setCurrentData(this.listGrid.data[this.listGrid.rowIndex].id)
        this.getGroupUser(this.listGrid.data[this.listGrid.rowIndex].id)
        return
      }
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/system/usergroup',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>
