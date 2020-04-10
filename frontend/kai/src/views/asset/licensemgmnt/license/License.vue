<!--
 Class Name  : License.vue
 Description : 라이선스 > 메인리스트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.16  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="2" >
          <div v-show="(activeTab !== 'list') && detailType === 'UPDATE'">
            <b-button class="move-btn" v-on:click="preMovePage('previous')">
              <img src="../../../../assets/img/icons/arrow-left@2x.png"/>
            </b-button>
            <b-button class="move-btn" v-on:click="preMovePage('next')">
              <img src="../../../../assets/img/icons/arrow-right@2x.png"/>
            </b-button>
          </div>
        </b-col>
        <b-col sm="10">
          <div class="comm-btn-box" role="group">
            <b-button variant="primary" class="px-3 sm" v-show="visibleOpenAttach" v-on:click="openUploadModal(listGrid.currentData)">{{labels.attachFile}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleCopy" v-on:click="copy">{{labels.copy}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleDelete" v-on:click="del">{{labels.del}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="12">
          <!--LIST-->
          <el-tabs v-model="activeTab" :before-leave="chgTab">
            <el-tab-pane  name="list" :label='labels.list'>
              <b-row>
                <b-col sm="12">
                  <b-row>
                    <b-col sm="6">
                      <div class="search-component">
                        <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
                      </div>
                    </b-col>
                    <div>
                      <el-popover placement="right" width="500" trigger="click" v-model="visibleSearchDetailFlag">
                        <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
                          {{labels.searchDetail}}
                        </b-row>
                        <b-row>
                        <div class='form-group col-md-6'>
                          <label for="ciId">{{labels.swlicenseId}}</label>
                          <input id="ciId" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swlicenseIdD">
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="classstructure">{{labels.name}}</label>
                          <input id="classstructure" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swlicnameD"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="ciname">{{labels.purchasetype}}</label>
                          <input id="ciname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.purchasetypeNmD"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="description">{{labels.purchaseunit}}</label>
                          <input id="description" class="formdata sm float-right" v-model="listGrid.searchParamDetail.purchaseunitD"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="company">{{labels.companyM}}</label>
                          <input id="company" class="formdata sm float-right" v-model="listGrid.searchParamDetail.companyD"/>
                        </div>
                        <div class='form-group col-md-6'>
                          <label for="software">{{labels.software}}</label>
                          <input id="software" class="formdata sm float-right" v-model="listGrid.searchParamDetail.softwareD"/>
                        </div>
                      </b-row>
                        <b-row>
                          <div class="col mt-2">
                            <div class='d-flex justify-content-end'>
                              <b-button variant="primary" class="px-3 sm" v-on:click="searchDetailReset">{{labels.reset}}</b-button>
                              <b-button variant="primary" class="px-3 sm" v-on:click="searchDetail">{{labels.search}}</b-button>
                            </div>
                          </div>
                        </b-row>
                        <el-button slot="reference" style="margin-top:4px !important" :class='searchDetailBtnClass'></el-button>
                      </el-popover>
                    </div>
                    <b-col>
                      <div class="float-right mb-1" role="group">
                        <el-button v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0 0 0 0; width:40px; height: 40px;">
                          <img src="../../../../assets/img/icons/icon_search_download.png" width="60%" />
                        </el-button>
                      </div>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col sm="12">
                      <p class="grid-line"></p>
                      <data-tables-server v-if="listGridFlag" ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" v-on:row-click="clickGridRow" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps"  :current-page.sync='listGrid.currentPage'>
                        <el-table-column :prop='listGrid.titles[0].prop' :label='labels.swlicenseId'    sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[1].prop' :label='labels.name'></el-table-column>
                        <el-table-column :prop='listGrid.titles[2].prop' :label='labels.purchasetype'   sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[3].prop' :label='labels.purchaseunit'   sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[4].prop' :label='labels.purchasenum'    sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                        <el-table-column :prop='listGrid.titles[5].prop' :label='labels.startDt'        sortable='custom' :width='listGrid.titles[5].width'>
                          <template slot-scope="props">
                            {{$util.date(props.row.startDt)}}
                          </template>
                        </el-table-column>
                        <el-table-column :prop='listGrid.titles[6].prop' :label='labels.endDt'        sortable='custom' :width='listGrid.titles[6].width'>
                          <template slot-scope="props">
                            {{$util.date(props.row.endDt)}}
                          </template>
                        </el-table-column>
                      </data-tables-server>
                    </b-col>
                  </b-row>
                </b-col>
              </b-row>
            </el-tab-pane>
            <!--DETAIL-->
            <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
              <LicenseDetail
                ref="licenseDetail"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:validMsg="validMsg"
              />
            </el-tab-pane>
            <!--SWCAT-->
            <el-tab-pane  name="swcat" :label='labels.licenseSwcat' v-if="(activeTab !== 'list')">
              <LicenseSwcat
                ref="licenseSwcat"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:swcatGrid="swcatGrid"
                v-bind:getSwcatList="getSwcatList"
                v-bind:validMsg="validMsg"
              />
            </el-tab-pane>
            <!--RELATION-->
            <el-tab-pane  name="relation" :label='labels.licenseRelation' v-if="(activeTab !== 'list')">
              <LicenseRelation
                ref="licenseRelation"
                v-bind:labels="labels"
                v-bind:authorities="authorities"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:relationGrid="relationGrid"
                v-bind:getRelationList="getRelationList"
                v-on:moveLicense="clickGridRow"
                v-bind:validMsg="validMsg"
              />
            </el-tab-pane>
            <!--LicenseFile File-->
            <el-tab-pane  name="licenseFile" :label='labels.attachFile' v-if="(activeTab !== 'list')">
              <LicenseFile
                ref="licenseFile"
                v-bind:labels="labels"
                v-bind:comboData="comboData"
                v-bind:detailType="detailType"
                v-bind:listGrid="listGrid"
                v-bind:validMsg="validMsg"
              />
             </el-tab-pane>
          </el-tabs>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>
<script>
import LicenseDetail from './LicenseDetail'
import LicenseSwcat from './LicenseSwcat'
import LicenseRelation from './LicenseRelation'
import LicenseFile from './LicenseFile'
export default {
  name: 'LICENSE',
  components: {
    'LicenseDetail': LicenseDetail,
    'LicenseSwcat': LicenseSwcat,
    'LicenseRelation': LicenseRelation,
    'LicenseFile': LicenseFile
  },
  data () {
    return {
      labels: {
        swlicenseId: '',
        swcatId: ''
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
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: [],
          value: ''
        }, {
          prop: 'ls',
          value: ''
        }, {
          prop: 'key',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swlicenseId',
          width: '140px'
        }, {
          prop: 'swlicname',
          width: '250px'
        }, {
          prop: 'purchasetypeNm',
          width: '120px'
        }, {
          prop: 'purchaseunit',
          width: '180px'
        }, {
          prop: 'purchasenum',
          width: '120px'
        }, {
          prop: 'startDt',
          width: '100px'
        }, {
          prop: 'endDt',
          width: '100px'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        ciExtNmData: {},
        total: 0,
        srchCnd: null,
        searchParamDetail: {
          swlicenseIdD: '',
          swlicnameD: '',
          purchasetypeNmD: '',
          purchaseunitD: '',
          companyD: '',
          softwareD: ''
        }
      },
      swcatGrid: {
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
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['swcatId', 'classstructureNm', 'publishername', 'publishername', 'swcatname', 'swcattype', 'swcatversion'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swcatId',
          width: '200px'
        }, {
          prop: 'classstructureNm',
          width: '180px'
        }, {
          prop: 'publishername',
          width: '200px'
        }, {
          prop: 'swcatname',
          width: '300x'
        }, {
          prop: 'swcattype',
          width: '100x'
        }, {
          prop: 'swcatversion',
          width: '100x'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        total: 0,
        srchCnd: null,
        searchParam: ''
      },
      relationGrid: {
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
          pageSize: 5,
          pageSizes: [5, 10]
        },
        data: [],
        filters: [{
          prop: ['swlicenseId', 'swlicname', 'swlictypeNm', 'purchasetypeNm', 'purchaseunit', 'purchasenum'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swlicenseId',
          width: '140px'
        }, {
          prop: 'swlicname',
          width: '250px'
        }, {
          prop: 'swlictypeNm',
          width: '150px'
        }, {
          prop: 'purchasetypeNm',
          width: '120px'
        }, {
          prop: 'purchaseunit',
          width: '180px'
        }, {
          prop: 'purchasenum',
          width: '120px'
        }, {
          prop: 'startDt',
          width: '100px'
        }, {
          prop: 'endDt',
          width: '100px'
        }],
        currentData: {},
        total: 0,
        searchParam: ''
      },
      // Auth
      authorities: {
        LICENSE_ATTACHMENT: false,
        LICENSE_ADD: false,
        LICENSE_SAVE: false,
        LICENSE_DELETE: false,
        LICENSE_SWCAT_ADD: false,
        LICENSE_SWCAT_SAVE: false,
        LICENSE_SWCAT_DELETE: false,
        LICENSE_RELATION_ADD: false,
        LICENSE_RELATION_SAVE: false,
        LICENSE_RELATION_DELETE: false
      },
      // Combo Data
      comboData: {
        orgSelect: [],
        statusSelect: [],
        licensetypeSelect: [],
        purchasetypeSelect: [],
        priceunitSelect: [],
        purchaseunitSelect: []
      },
      // Global Var
      orgId: '',
      activeTab: 'list',
      detailType: 'INSERT',
      searchType: 'NORMAL',
      searchParam: '',
      ciDialogParam: '',
      // Valid Msg
      validMsg: {
        nameMsg: '',
        descriptionMsg: '',
        purchaseDtMsg: '',
        purchaseunitMsg: '',
        purchasenumMsg: '',
        startDt: ''
      },
      visibleSearchDetailFlag: false,
      listGridFlag: false
    }
  },
  watch: {
    '$route': function () {
      if (!this.$util.isNull(this.$route.params.id)) {
        this.paramInit()
      } else if (!this.$util.isNull(this.$route.params.ls)) {
        this.ls()
      }
    },
    activeTab: function (val) {
      if (val === 'list') {
        this.validMsg.nameMsg = ''
        this.validMsg.descriptionMsg = ''
        this.relationGrid.searchParam = ''
        this.relationGrid.filters[0].value = ''
        this.swcatGrid.searchParam = ''
        this.swcatGrid.filters[0].value = ''
        this.getListData(this.listGrid.srchCnd)
      }
    },
    'listGrid.currentData.swlicname': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.nameMsg = ''
      }
    },
    'listGrid.currentData.swlicdescription': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.descriptionMsg = ''
      }
    },
    'listGrid.currentData.purchaseunit': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.purchaseunitMsg = ''
      }
    },
    'listGrid.currentData.purchasenum': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.purchasenumMsg = ''
      }
    },
    'listGrid.currentData.purchaseDt': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.purchaseDtMsg = ''
      }
    },
    'listGrid.currentData.startDt': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.startDt = ''
      }
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.LICENSE_ADD && (this.activeTab === 'list')
    },
    visibleCopy: function () {
      return this.authorities.LICENSE_ADD && (this.activeTab !== 'list') && this.detailType === 'UPDATE'
    },
    visibleSave: function () {
      return this.authorities.LICENSE_SAVE && (this.activeTab !== 'list')
    },
    visibleDelete: function () {
      return this.authorities.LICENSE_DELETE && (this.activeTab !== 'list')
    },
    visibleOpenAttach: function () {
      return this.authorities.LICENSE_ATTACHMENT && (this.activeTab !== 'list') && this.detailType === 'UPDATE'
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
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.orgId = this.$store.getters.getUser.orgId
      this.listGridFlag = true
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    chgTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        if (this.isChkChg()) {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.listGrid.currentData = {}
              $this.swcatGrid.data = []
              $this.relationGrid.data = []
              $this.activeTab = 'list'
            })
        }
      }
      return rtnFlag
    },
    isChkChg: function () {
      return this.listGrid.currentData.state === 'CHANGED'
    },
    isValidationCheck: function () {
      let rtnFlag = true
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      let swlicname = this.listGrid.currentData.swlicname
      let swlicdescription = this.listGrid.currentData.swlicdescription
      let purchaseunit = this.listGrid.currentData.purchaseunit
      let purchasenum = this.listGrid.currentData.purchasenum
      let purchaseDt = this.listGrid.currentData.purchaseDt
      let startDt = this.listGrid.currentData.startDt
      if (this.$util.isNull(swlicname)) {
        this.validMsg.nameMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(swlicdescription)) {
        this.validMsg.descriptionMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(purchaseunit)) {
        this.validMsg.purchaseunitMsg = this.labels.inputData
        rtnFlag = false
        this.activeTab = 'detail'
      }
      if (this.$util.isNull(purchasenum)) {
        this.validMsg.purchasenumMsg = this.labels.inputData
        rtnFlag = false
        this.activeTab = 'detail'
      }
      if (this.$util.isNull(purchaseDt)) {
        this.validMsg.purchaseDtMsg = this.labels.inputData
        rtnFlag = false
        this.activeTab = 'detail'
      }
      if (this.$util.isNull(startDt)) {
        this.validMsg.startDtMsg = this.labels.inputData
        rtnFlag = false
        this.activeTab = 'detail'
      }
      return rtnFlag
    },
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'comboData.orgSelect', query: '/episode/api/system/common/organization'},
          {dataObj: 'comboData.statusSelect', domainId: 'LICENSESTATUS'},
          {dataObj: 'comboData.licensetypeSelect', domainId: 'LICENSETYPE'},
          {dataObj: 'comboData.purchasetypeSelect', domainId: 'LICENSEPURCHASETYPE'},
          {dataObj: 'comboData.priceunitSelect', domainId: 'PRICEUNIT'},
          {dataObj: 'comboData.purchaseunitSelect', domainId: 'LICENSEPURCHASEUNIT'}
        ]
      }
      this.$util.getComboData(param)
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'descending'
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
        orgId: this.orgId,
        searchType: this.searchType,
        searchParam: searchParam,
        swlicenseIdD: this.listGrid.searchParamDetail.swlicenseIdD,
        swlicnameD: this.listGrid.searchParamDetail.swlicnameD,
        purchasetypeNmD: this.listGrid.searchParamDetail.purchasetypeNmD,
        purchaseunitD: this.listGrid.searchParamDetail.purchaseunitD,
        companyD: this.listGrid.searchParamDetail.companyD,
        softwareD: this.listGrid.searchParamDetail.softwareD}
      }
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    getRowDataAll: function (id) {
      this.getRowData(id)
      this.getSwcatList(id)
      this.getRelationList(id)
    },
    getRowData: function (id) {
      this.listGrid.currentData = {}
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getSwcatList: function (id) {
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license/' + id + '/swcat'
      ).then(res => {
        this.swcatGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRelationList: function (id) {
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license/' + id + '/relation'
      ).then(res => {
        this.relationGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    openUploadModal: function (data) {
      this.$refs.licenseFile.openUploadModal(data)
    },
    add: function () {
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license/licenseId?orgId=' + this.orgId
      ).then(res => {
        this.activeTab = 'detail'
        this.detailType = 'INSERT'
        this.listGrid.currentData = {
          orgId: this.orgId,
          swlicstatus: 'ACTIVE',
          swlicenseId: res.data,
          swlicname: '',
          swlicdescription: '',
          managerId: '',
          managerNm: '',
          swlictype: '',
          purchasetype: '',
          companyId: '',
          poId: '',
          purchaseunit: '',
          purchaseprice: '',
          purchaseDt: new Date(),
          purchasenum: '',
          remainprice: '',
          startDt: new Date(),
          endDt: ''
        }
        this.swcatGrid.data = []
        this.relationGrid.data = []
      }).catch(error => {
        console.log(error)
      })
    },
    copy: function () {
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license/licenseId?orgId=' + this.orgId
      ).then(res => {
        this.activeTab = 'detail'
        this.detailType = 'INSERT'
        this.listGrid.currentData.state = 'ADDED'
        delete this.listGrid.currentData.id
        this.listGrid.currentData.updateNm = null
        this.listGrid.currentData.updateDt = null
        this.listGrid.currentData.swlicenseId = res.data
        this.swcatGrid.data.forEach(data => {
          data.id = null
        })
        this.relationGrid.data = []
      }).catch(error => {
        console.log(error)
      })
    },
    saveSwcat: function () {
      let dataList = []
      this.swcatGrid.data.forEach(target => {
        if (this.$util.isNull(target.id)) {
          let data = {
            orgId: this.listGrid.currentData.orgId,
            swlicenseId: this.listGrid.currentData.swlicenseId,
            swcatId: target.swcatId
          }
          dataList.push(data)
        }
      })
      return dataList
    },
    save: function () {
      if (this.isValidationCheck()) {
        let swcatList = this.saveSwcat()
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/asset/licensemgmnt/license/' + this.listGrid.currentData.id : '/episode/api/asset/licensemgmnt/license'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.listGrid.currentData.swcatList = swcatList
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.detailType = 'UPDATE'
            this.getRowDataAll(res.data.id)
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
      } else if (this.swcatGrid.data.length > 0 || this.relationGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        let $this = this
        this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
          this.$http.delete(
            '/episode/api/asset/licensemgmnt/license/' + $this.listGrid.currentData.id
          ).then(res => {
            $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
            $this.activeTab = 'list'
            $this.searchType = 'NORMAL'
            $this.getListData($this.listGrid.srchCnd)
          }).catch(error => {
            $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
            console.log(error)
          })
        })
      }
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.searchType = 'NORMAL'
      this.listGrid.filters[0].value = this.searchParam
      this.listGrid.filters[1].value = ''
      this.listGrid.filters[2].value = ''
    },
    searchDetail: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.currentPage = 1
      this.listGrid.searchParamDetail.swlicenseIdD = ''
      this.listGrid.searchParamDetail.swlicnameD = ''
      this.listGrid.searchParamDetail.purchasetypeNmD = ''
      this.listGrid.searchParamDetail.purchaseunitD = ''
      this.listGrid.searchParamDetail.companyD = ''
      this.listGrid.searchParamDetail.softwareD = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'id'
      let sort = 'desscending'
      if (srchCnd != null && srchCnd !== undefined && srchCnd !== 'undefined') {
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
      }
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        searchType: this.searchType,
        searchParam: searchParam,
        swlicenseIdD: this.listGrid.searchParamDetail.swlicenseIdD,
        swlicnameD: this.listGrid.searchParamDetail.swlicnameD,
        purchasetypeNmD: this.listGrid.searchParamDetail.purchasetypeNmD,
        purchaseunitD: this.listGrid.searchParamDetail.purchaseunitD,
        companyD: this.listGrid.searchParamDetail.companyD,
        softwareD: this.listGrid.searchParamDetail.softwareD}
      }
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license',
        params
      ).then(res => {
        let fileName = 'license'
        let fields = [
          {value: 'swlicenseId', label: this.labels.swlicenseId},
          {value: 'swlicname', label: this.labels.name},
          {value: 'purchasetypeNm', label: this.labels.purchasetype},
          {value: 'purchaseunit', label: this.labels.purchaseunit},
          {value: 'purchasenum', label: this.labels.purchasenum},
          {value: 'startDt', label: this.labels.startDt},
          {value: 'endDt', label: this.labels.endDt}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    clickGridRow: function (row) {
      this.activeTab = 'detail'
      this.detailType = 'UPDATE'
      this.listGrid.rowIndex = this.listGrid.data.indexOf(row)
      this.getRowDataAll(row.id)
    },
    paramInit: function () {
      if (!this.$util.isNull(this.$route.params.id)) {
        this.activeTab = 'detail'
        this.detailType = 'UPDATE'
        this.getRowDataAll(this.$route.params.id)
      }
    },
    ls: function () {
      if (!this.$util.isNull(this.$route.params.ls)) {
        if (this.$route.params.ls === 'COMPANY') {
          this.listGrid.searchParamDetail.companyD = this.$route.params.key
        } else {
          this.listGrid.searchParamDetail.softwareD = this.$route.params.key
        }
      }
    },
    preMovePage: function (moveStr) {
      if (this.isChkChg()) {
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
      this.relationGrid.searchParam = ''
      this.relationGrid.filters[0].value = ''
      this.swcatGrid.searchParam = ''
      this.swcatGrid.filters[0].value = ''
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
        this.getRowDataAll(this.listGrid.data[this.listGrid.rowIndex].id)
        return
      }
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'descending'
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
        orgId: this.orgId,
        searchType: this.searchType,
        searchParam: searchParam,
        swlicenseIdD: this.listGrid.searchParamDetail.swlicenseIdD,
        swlicnameD: this.listGrid.searchParamDetail.swlicnameD,
        purchasetypeNmD: this.listGrid.searchParamDetail.purchasetypeNmD,
        purchaseunitD: this.listGrid.searchParamDetail.purchaseunitD,
        companyD: this.listGrid.searchParamDetail.companyD,
        softwareD: this.listGrid.searchParamDetail.softwareD}
      }
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
        this.listGrid.currentPage = srchCnd.page
        this.getRowDataAll(this.listGrid.data[this.listGrid.rowIndex].id)
      }).catch(error => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.initialize()
    this.paramInit()
    this.ls()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.swcatGrid.data = []
    this.relationGrid.data = []
    this.orgSelect = []
    this.statusSelect = []
    this.licensetypeSelect = []
    this.purchasetypeSelect = []
    this.priceunitSelect = []
    this.purchaseunitSelect = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
