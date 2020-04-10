<!--
 File Name  : Location.vue
 Description : 위치
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.20  이정호  최초 생성
-->
<template>
   <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class='row'>
            <div class='col-md-12'>
              <div class="comm-btn-box" role="group">
                <b-button class='px-3 sm' variant="primary" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
                <b-button class='px-3 sm' variant="primary" v-show="visibleAdd"  v-on:click="add">{{labels.new}}</b-button>
              </div>
            </div>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="3">
          <Tree
            v-bind:tree="tree"
            ref="tree"
            v-on:clickNode="clickNode"
            v-on:clickAddBtn="clickAddBtn"
          />
        </b-col>
      <b-col sm="9">
      <!--LIST-->
      <el-tabs v-model="activeTab" :before-leave="chgTab">
        <el-tab-pane  name="list" :label='labels.list'>
            <b-row>
              <div class="col-md-6" style="padding-top:20px; padding-left:20px;">
                <div class="search-component sm">
                  <input class="form-control formdata sm" style="width: 80%;" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder=labels.searchA v-on:focus="filterFocus"/>
                </div>
              </div>
              <div class='col-md-6'>
                <div class="btn-group float-right mt-2" role="group">
                  <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                    <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                  </el-button>
                </div>
              </div>
            </b-row>
            <b-row class="mt-2">
              <div class='col'>
                <p class='grid-line'></p>
                <data-tables v-if="listGridFlag" style="width: 100%" layout='table, pagination' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' @filtered-data="handleFilteredData" :filters='listGrid.filters' :pagination-props='listGrid.pageProps' v-on:row-click="clickGridRow" :current-page.sync='listGrid.currentPage'>
                  <el-table-column :prop='listGrid.titles[0].prop' :label='labels.id'               sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                  <el-table-column :prop='listGrid.titles[1].prop' :label='labels.parent'           sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                  <el-table-column :prop='listGrid.titles[2].prop' :label='labels.locationId'       sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                  <el-table-column :prop='listGrid.titles[3].prop' :label='labels.namekor'          sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                  <el-table-column :prop='listGrid.titles[4].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                  <el-table-column :prop='listGrid.titles[5].prop' :label='labels.description'      sortable='custom' ></el-table-column>
                </data-tables>
              </div>
            </b-row>
        </el-tab-pane>
        <!--DETAIL-->
        <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
          <b-row>
            <div class="form-group col-md-3">
              <label for="id">{{labels.id}}</label>
              <input id="id" v-model="listGrid.currentData.id" class="formdata sm" disabled="disabled"/>
            </div>
            <div class="form-group col-md-3">
              <label for="orgId">{{labels.orgId}}<span class="require-mark">*</span></label>
              <el-select style="width:100%"  v-model="listGrid.currentData.orgId" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)" :disabled="listGrid.currentData.hasOwnProperty('id')">
                <el-option v-for="org in orgSelect" :prop="org.orgId" :value="org.orgId" :key="org.orgId">
                  {{org.orgId}}
                </el-option>
              </el-select>
            </div>
             <div class="form-group col-md-3">
              <label for="parent">{{labels.parent}}</label>
              <div class="search-component sm">
                <input id="parent" v-model="listGrid.currentData.parent" class="formdata sm" disabled="disabled"/>
                <a v-show="!listGrid.currentData.hasOwnProperty('id')" class="search-button cursor-pointer" v-on:click="openParentDialog">search</a>
              </div>
            </div>
            <div class="form-group col-md-3">
              <label for="locationId">{{labels.locationId}}<span class="require-mark">*</span></label>
              <input id="locationId" v-model="listGrid.currentData.locationId" v-on:blur="blurLocationId" v-on:change="changeValue(listGrid.currentData)" class="formdata sm"  :disabled="listGrid.currentData.hasOwnProperty('id')" v-bind:maxlength="30" />
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.locationId)" v-text="validMsg.locationId"></p>
            </div>
          </b-row>
          <b-row>
            <div class="form-group col-md-3">
              <label for="namekor">{{labels.namekor}}<span class="require-mark">*</span></label>
              <input id="namekor" v-model="listGrid.currentData.namekor" class="formdata sm" v-on:blur="validateKor" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="50"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.namekor)" v-text="validMsg.namekor"></p>
            </div>
            <div class="form-group col-md-3">
              <label for="nameeng">{{labels.nameeng}}<span class="require-mark">*</span></label>
              <input id="nameeng" v-model="listGrid.currentData.nameeng" class="formdata sm" v-on:blur="validateEng" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="50"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.nameeng)" v-text="validMsg.nameeng"></p>
            </div>
            <div class="form-group col-md-3">
              <label for="status">{{labels.status}}<span class="require-mark">*</span></label>
              <el-select v-model="listGrid.currentData.status" v-on:change="changeValue(listGrid.currentData)" style="width:100%" :placeholder="labels.selectData">
                <el-option v-for="status in statusSelect" :prop="status.value" :value="status.value" :key="status.key">
                  {{status.value}}
                </el-option>
              </el-select>
            </div>
            <div class="form-group col-md-3">
              <label for="type">{{labels.type}}<span class="require-mark">*</span></label>
              <el-select style="width:100%"  v-model="listGrid.currentData.type" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.selectData">
                <el-option v-for="type in typeSelect" :prop="type.value" :value="type.value" :key="type.key">
                  {{type.value}}
                </el-option>
              </el-select>
            </div>
          </b-row>
          <b-row>
            <div class="form-group col-md-12">
             <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
             <input id="description" v-model="listGrid.currentData.description" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" v-on:blur="validateDes" autocomplete="off" v-bind:maxlength="50"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.description)" v-text="validMsg.description"></p>
           </div>
          </b-row>
          <b-row>
            <div class='form-group col-md-3'>
               <label for="createId">{{labels.createId}}</label>
               <input id="createId" class="form-control formdata sm" v-model="listGrid.currentData.createNm" placeholder="CREATER" disabled="disabled" />
             </div>
             <div class='form-group col-md-3'>
               <label for="createDt">{{labels.createDt}}</label>
               <input id="createDt" class="form-control formdata sm" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled"/>
             </div>
             <div class='form-group col-md-3'>
               <label for="updateId">{{labels.updateId}}</label>
               <input id="updateId" class="form-control formdata sm" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled" />
             </div>
             <div class='form-group col-md-3'>
               <label for="updateDt">{{labels.updateDt}}</label>
               <input id="updateDt" class="form-control formdata sm" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
             </div>
          </b-row>
        </el-tab-pane>
        </el-tabs>
      </b-col>

    <!-- Parent Dialog -->
      <el-dialog :title="labels.parentDialogTitle" :visible.sync="showParentDialogFlag" width=290px>
        <b-row>
          <div class="col">
            <div class="treeDialog">
              <TreeParent
               v-bind:tree="treeParent"
               ref="treeParent"
               v-on:clickNode="clickPNode"/>
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
    </b-row>
    </b-card>
  </div>
</template>
<script>
import Tree from '@/views/common/TreeCom'
export default {
  name: 'LOCATION',
  components: {
    'Tree': Tree,
    'TreeParent': Tree
  },
  data () {
    return {
      labels: {
        title: '',
        root: '',
        list: '',
        detail: '',
        id: '',
        orgId: '',
        parent: '',
        status: '',
        description: '',
        hierarchypath: '',
        createDt: '',
        createId: '',
        updateDt: '',
        updateId: '',
        locationId: '',
        namekorMsg: '',
        nameengMsg: '',
        chkChgDataMsg: ''
      },
      tree: {
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
          return true
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
          prop: ['id', 'parent', 'locationId', 'namekor', 'status', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'id',
          label: 'id.',
          width: '100px'
        }, {
          prop: 'parent',
          label: 'parent',
          width: '150px'
        }, {
          prop: 'locationId',
          label: 'locationId',
          width: '150px'
        }, {
          prop: 'namekor',
          label: 'namekor',
          width: '250x'
        }, {
          prop: 'status',
          label: 'status',
          width: '100px'
        }, {
          prop: 'description',
          label: 'description'
        }],
        columns: ['id', 'parent', 'locationId', 'namekor', 'status', 'description'],
        columnNames: ['id', 'parent', 'locationId', 'namekor', 'status', 'description'],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      orgSelect: [],
      statusSelect: [],
      typeSelect: [],
      classIconSelect: [],
      ancestor: [],
      orgId: '',
      activeTab: 'list',
      showParentDialogFlag: false,
      showTreeMenu: false,
      listGridFlag: false,
      authorities: {
        LOCATION_SAVE: false,
        LOCATION_ADD: false
      },
      validMsg: {
        locationId: '',
        description: '',
        namekor: '',
        nameeng: ''
      }
    }
  },
  computed: {
    visibleSave: function () {
      return this.authorities.LOCATION_SAVE && (this.activeTab !== 'list')
    },
    visibleAdd: function () {
      return this.authorities.LOCATION_ADD && (this.activeTab === 'list')
    }
  },
  watch: {
    activeTab: function (val) {
      this.showParentDialogFlag = false
    },
    'listGrid.currentData.id': function (val) {
      this.showParentDialogFlag = false
    },
    'listGrid.currentData.locationId': function (val) {
      if (!this.$util.isNull(val)) {
        this.listGrid.currentData.locationId = this.changeUpper(val)
      }
    },
    'showParentDialogFlag': function () {
      this.treeParent.selectData = {}
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.orgId = this.$store.getters.getUser.orgId
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'statusSelect', domainId: 'LOCATIONSTATUS'},
          {dataObj: 'typeSelect', domainId: 'LOCATIONTYPE'}
        ]
      }
      this.listGridFlag = true
      this.$util.getComboData(param)
      this.$http.get('/episode/api/system/common/organization').then(res => {
        this.orgSelect = res.data
      }).catch(error => {
        console.log(error)
      })
      this.getTreeData()
      this.getListData()
    },
    makeHierarchypath: function () {
      let parent = this.listGrid.currentData.parent
      let locationId = this.listGrid.currentData.locationId
      let hierarchypath = ''
      if (!this.$util.isNull(parent)) {
        let data = this.treeParent.selectData
        hierarchypath = data.hierarchypath
      }
      this.listGrid.currentData.hierarchypath = hierarchypath + '/' + locationId
    },
    validateDes: function () {
      if (this.$util.isNull(this.listGrid.currentData.description)) {
        this.validMsg.description = this.labels.inputData
        return false
      }
      this.validMsg.description = ''
      return true
    },
    validateKor: function () {
      let namekor = this.listGrid.currentData.namekor
      if (this.$util.isNull(namekor)) {
        this.validMsg.namekor = this.labels.inputData
        return false
      }
      this.validMsg.namekor = ''
      return true
    },
    validateEng: function () {
      let nameeng = this.listGrid.currentData.nameeng
      if (this.$util.isNull(nameeng)) {
        this.validMsg.nameeng = this.labels.inputData
        return false
      }
      this.validMsg.nameeng = ''
      return true
    },
    changeUpper: function (val) {
      return val.toUpperCase()
    },
    getPTreeData: function () {
      this.$http.get(
        '/episode/api/system/common/locationtree?orgId=' + this.orgId
      ).then(res => {
        this.treeParent.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getTreeData: function () {
      this.$http.get(
        '/episode/api/system/common/locationtree?orgId=' + this.orgId
      ).then(res => {
        this.tree.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function () {
      this.$http.get(
        '/episode/api/system/location?orgId=' + this.orgId
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
        '/episode/api/system/location/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/location/' + id + '/ancestor'
      ).then(res => {
        this.ancestor = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    changeValue: function (data) {
      if (data.hasOwnProperty('id')) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    },
    blurLocationId: function () {
      if (this.listGrid.currentData.state === 'ADDED') {
        let locationId = this.listGrid.currentData.locationId
        if (this.$util.isNull(locationId)) {
          this.validMsg.locationId = this.labels.inputData
          return false
        }
        let parent = this.listGrid.currentData.parent
        let locationName = ''
        if (parent !== '') locationName = parent + '-' + locationId
        else locationName = locationId

        if (this.listGrid.currentData.locationId !== '') {
          this.$http.get(
            '/episode/api/system/location?orgId=' + this.listGrid.currentData.orgId + '&locationId=' + locationName
          ).then(res => {
            if (res.data.length > 0) {
              this.validMsg.locationId = this.labels.locationIdExist
              this.listGrid.currentData.locationId = ''
            } else {
              this.validMsg.locationId = ''
              this.makeHierarchypath()
            }
          }).catch(error => {
            console.log(error)
          })
          if (this.validMsg.locationId !== '') {
            return false
          } else {
            return true
          }
        }
      } else {
        return true
      }
    },
    isValidationCheck: function () {
      let locationId = this.blurLocationId()
      let namekor = this.validateKor()
      let nameeng = this.validateEng()
      let description = this.validateDes()
      if (locationId && namekor && nameeng && description) {
        return true
      }
    },
    save: function () {
      if (this.isValidationCheck()) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/location/' + this.listGrid.currentData.id : '/episode/api/system/location'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.listGrid.currentData = res.data
            this.getTreeData()
            this.getListData()
            this.getRowData(res.data.id)
          }).catch(error => {
            console.log(error)
          })
        }
      }
    },
    del: function () {
      if (this.listGrid.currentData.id === undefined || this.listGrid.currentData.id === '') {
        this.activeTab = 'list'
      } else if (this.ancestor.length > 1) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        this.$http.delete('/episode/api/system/location/' + this.listGrid.currentData.id).then(res => {
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.activeTab = 'list'
          this.getTreeData()
          this.getListData()
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
    exportFilter: function () {
      let lang = this.$store.getters.getUser.lang
      let fileName = 'location'
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'parent', label: this.labels.parent},
        {value: 'locationId', label: this.labels.locationId},
        {value: 'namekor', label: this.labels.namekor},
        {value: 'status', label: this.labels.status},
        {value: 'description', label: this.labels.description}
      ]
      if (lang === 'ko-kr') {
        fileName = this.labels.title
      }
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    },
    dateTime: function (date) {
      return this.$util.dateTimeWithTimeZone(date)
    },
    clickPNode: function (node) {
      this.treeParent.selectData = node.data
    },
    clickNode: function (node) {
      if (this.activeTab !== 'list') {
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              if (!$this.$util.isNull(node)) {
                $this.listGrid.filters[0].value = ''
                $this.listGrid.searchParam = ''
                let treeData = node.data
                $this.listGrid.data = $this.listGrid.dataBak
                $this.listGrid.data = $this.listGrid.data.filter((data, index) => {
                  return (data.hierarchypath.indexOf(treeData.hierarchypath + '/') > -1 || data.locationId === treeData.locationId || treeData.locationId === 'LOCATION')
                })
              }
              for (let key in $this.validMsg) {
                $this.validMsg[key] = ''
              }
              $this.getRowData(node.data.id)
            }
          )
          return
        }
      }
      if (!this.$util.isNull(node)) {
        this.listGrid.filters[0].value = ''
        let treeData = node.data
        this.listGrid.data = this.listGrid.dataBak
        this.listGrid.data = this.listGrid.data.filter((data, index) => {
          return (data.hierarchypath.indexOf(treeData.hierarchypath + '/') > -1 || data.locationId === treeData.locationId || treeData.locationId === 'LOCATION')
        })
      }
      if (node.text === 'LOCATION') {
        return
      }
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      this.getRowData(node.data.id)
    },
    clickAddBtn: function (parent) {
      if (this.activeTab !== 'list') {
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              let nodes = $this.$refs.tree.$refs.tree.findAll({data: {id: $this.listGrid.currentData.id}})
              nodes.select()
              $this.tree.selectData = $this.listGrid.currentData
              $this.save()
            },
            function () {
              for (let key in $this.validMsg) {
                $this.validMsg[key] = ''
              }
              $this.addData(parent)
            }
          )
          return
        }
      }
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      this.addData(parent)
    },
    addData: function (parent) {
      this.activeTab = 'detail'
      this.treeParent.selectData = parent
      this.listGrid.currentData = {
        orgId: 'KAI',
        classicon: 'stack-48.png',
        parent: parent.locationId === 'LOCATION' ? '' : parent.locationId,
        status: 'ACTIVE',
        type: 'OPERATING'
      }
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
    },
    chgTab: function (activeTab, oldTab) {
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
              $this.listGrid.filters[0].value = ''
              for (let key in $this.validMsg) {
                $this.validMsg[key] = ''
                $this.userFlag = true
              }
            })
        } else {
          this.listGrid.currentData = {}
          this.listGrid.searchParam = ''
          this.listGrid.filters[0].value = ''
          for (let key in this.validMsg) {
            this.validMsg[key] = ''
            this.userFlag = true
          }
        }
      }
      return rtnFlag
    },
    clickGridRow: function (row) {
      this.activeTab = 'detail'
      this.tree.expandKeys = [row.id]
      this.$refs.tree.$refs.tree.setCurrentKey(row.id)
      this.getRowData(row.id)
      // this.activeTab = 'detail'
      // this.listGrid.rowIndex = this.listGrid.data.indexOf(row)
      // let nodes = this.$refs.tree.$refs.tree.findAll({data: {id: row.id}})
      // nodes.select()
      // this.getRowData(row.id)
    },
    changeRow: function (row) {
      if (!this.$util.isNull(row.id)) {
        row.state = 'CHANGED'
      }
    },
    add: function () {
      this.listGrid.currentData = {orgId: 'KAI', classicon: 'square@2x.png', parent: '', status: 'ACTIVE', type: 'OPERATING'}
      // this.listGrid.currentData = {orgId: 'KAI', classicon: 'square@2x.png', parent: '', status: 'ACTIVE', type: 'OPERATING', state: 'ADDED'}
      this.activeTab = 'detail'
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
    },
    clickParentTree: function (node) {
      node.toggleExpand()
      this.treeParent.selectData = node.data
    },
    openParentDialog: function () {
      this.showParentDialogFlag = true
      this.getPTreeData()
    },
    confirmParentDialog: function () {
      this.changeValue(this.listGrid.currentData)
      let treedata = this.treeParent.selectData
      if (treedata !== undefined) {
        this.listGrid.currentData.parent = (treedata.locationId === 'LOCATION' ? '' : treedata.locationId)
        this.showParentDialogFlag = false
        this.makeHierarchypath()
      }
    },
    cancleParentDialog: function () {
      this.showParentDialogFlag = false
      this.listGrid.currentData.parent = ''
    },
    filterFocus: function () {
      this.listGrid.data = this.listGrid.dataBak
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.tree.rawData = []
    this.treeParent.rawData = []
    this.orgSelect = []
    this.statusSelect = []
    this.typeSelect = []
    this.ancestor = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.$nextTick(function () {
      this.listGridFlag = false
      this.tree.showTreeFlag = false
    })
  }
}
</script>
