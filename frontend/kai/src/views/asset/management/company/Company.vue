<!--
 Class Name  : Company.vue
 Description : 업체관리
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.28  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class="comm-btn-box" role="group">
            <b-button variant="primary" class="px-3 sm" v-on:click="openDetailDialog()" v-show="visibleAdd">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="6">
          <div class="search-component">
            <input class="form-control mt-2" style="width: 80%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
          </div>
        </b-col>
        <b-col sm="6">
          <div class="float-right mt-1 mb-1" role="group">
            <el-button v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0 0 0 0; width:40px; height: 40px;">
              <img src="../../../../assets/img/icons/icon_search_download.png" width="60%" />
            </el-button>
          </div>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="12">
          <p class="grid-line"></p>
          <data-tables-server v-if="listGridFlag" ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps" :current-page.sync='listGrid.currentPage'>
            <el-table-column :prop='listGrid.titles[0].prop' :label='labels.id'           sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[1].prop' :label='labels.companyname'  sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[2].prop' :label='labels.description'  sortable='custom' ></el-table-column>
            <el-table-column :prop='listGrid.titles[3].prop' :label='labels.companytype'  sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[4].prop' :label='labels.companynum'   sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[5].prop' :label='labels.companytel'   sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
            <el-table-column :prop='listGrid.titles[6].prop' :label='labels.ownername'    sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
            <el-table-column align="right" width="100" >
              <template slot-scope="props">
                <el-button v-on:click="clickGridRow(props.row)" :title="labels.detail" style="padding: 0; width:30px; height: 30px;">
                  <img src="@/assets/img/icons/icon_edit.png" width="60%" />
                </el-button>
              <el-button v-on:click="del(props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;" v-show="visibleDelete">
                  <img src="@/assets/img/icons/icon_delete.png" width="60%" />
                </el-button>
              </template>
            </el-table-column>
          </data-tables-server>
        </b-col>
      </b-row>
    </b-card>
    <!-- Detail dialog -->
    <el-dialog :title="labels.detail" :visible.sync="showDetailDialogFlag" width=800px  v-on:close="closeDetailDialog">
      <b-row>
        <div class="form-group col-md-3">
          <label for="companyId">{{labels.id}}</label>
          <div class="search-component sm">
            <input id="companyId" v-model="listGrid.currentData.companyId" class="formdata sm" autocomplete="off" disabled="disabled"/>
          </div>
        </div>
        <div class="form-group col-md-9">
          <label for="companyname">{{labels.companyname}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <input id="companyname" v-model="listGrid.currentData.companyname" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" autocomplete="off" v-bind:maxlength="100"/>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.companynameMsg)" v-text="validMsg.companynameMsg"></p>
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="form-group col-md-3">
          <label for="companytype">{{labels.companytype}}<span class="require-mark">*</span></label>
          <div class="search-component sm">
            <el-select v-model="listGrid.currentData.companytype" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.selectData">
              <el-option v-for="companytype in companytypeSelect" :prop="companytype.key" :value="companytype.key" :label="companytype.value" :key="companytype.key"></el-option>
            </el-select>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.companytypeMsg)" v-text="validMsg.companytypeMsg"></p>
          </div>
        </div>
        <div class="form-group col-md-3">
          <label for="companynum">{{labels.companynum}}</label>
          <div class="search-component sm">
            <input id="companynum" v-number v-model="listGrid.currentData.companynum" v-on:change="changeValue(listGrid.currentData)" v-on:blur="chkCompanyNum" class="formdata sm" autocomplete="off" v-bind:maxlength="10"/>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.companynumMsg)" v-text="validMsg.companynumMsg"></p>
          </div>
        </div>
        <div class="form-group col-md-3">
          <label for="companytel">{{labels.companytel}}</label>
          <div class="search-component sm">
            <input id="companytel" v-number v-model="listGrid.currentData.companytel" v-on:change="changeValue(listGrid.currentData)" v-on:blur="chkCompanyTel" class="formdata sm" autocomplete="off" v-bind:maxlength="11"/>
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.companytelMsg)" v-text="validMsg.companytelMsg"></p>
          </div>
        </div>
        <div class="form-group col-md-3">
          <label for="ownername">{{labels.ownername}}</label>
          <div class="search-component sm">
            <input id="ownername" v-model="listGrid.currentData.ownername" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" autocomplete="off" v-bind:maxlength="20"/>
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="form-group col-md-12">
          <label for="companydescription">{{labels.description}}</label>
          <div class="search-component sm">
            <input id="companydescription" v-model="listGrid.currentData.companydescription" v-on:change="changeValue(listGrid.currentData)" class="formdata sm" autocomplete="off" v-bind:maxlength="255"/>
          </div>
        </div>
      </b-row>
      <b-row>
        <div class='form-group col-md-6'>
          <label for="application_createId">{{labels.createNm}}</label>
          <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
        </div>
        <div class='form-group col-md-6'>
          <label for="application_createDt">{{labels.createDt}}</label>
          <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
        </div>
      </b-row>
      <b-row>
        <div class='form-group col-md-6'>
          <label for="application_updateId">{{labels.updateNm}}</label>
          <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
        </div>
        <div class='form-group col-md-6'>
          <label>{{labels.updateDt}}</label>
          <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
        </div>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="save" v-show="visibleSave">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="closeDetailDialog">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'COMPANY',
  data () {
    return {
      labels: {
        id: '',
        companyname: ''
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
          pageSize: 5,
          pageSizes: [10, 15, 20]
        },
        data: [],
        dataBak: [],
        filters: [{
          prop: ['companyId', 'companyname', 'companydescription'],
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'companyId',
          width: '100px'
        }, {
          prop: 'companyname',
          width: '150px'
        }, {
          prop: 'companydescription',
          width: '200px'
        }, {
          prop: 'companytypenm',
          width: '100px'
        }, {
          prop: 'companynum',
          width: '120px'
        }, {
          prop: 'companytel',
          width: '130px'
        }, {
          prop: 'ownername',
          width: '100px'
        }],
        currentData: {},
        total: 0,
        srchCnd: null
      },
      // Auth
      auth: {
        showBtnSaveFlag: false,
        showBtnDelFlag: false
      },
      authorities: {
        COMPANY_ADD: false,
        COMPANY_SAVE: false,
        COMPANY_DELETE: false
      },
      // Gloval var
      orgId: '',
      searchParam: '',
      // Gloval Flag
      showDetailDialogFlag: false,
      listGridFlag: false,
      // Combo Data
      companytypeSelect: [],
      // Valid Msg
      validMsg: {
        companynameMsg: '',
        companytypeMsg: '',
        companytelMsg: '',
        companynumMsg: ''
      }
    }
  },
  computed: {
    visibleAdd: function () {
      return this.authorities.COMPANY_ADD
    },
    visibleSave: function () {
      return this.authorities.COMPANY_SAVE
    },
    visibleDelete: function () {
      return this.authorities.COMPANY_DELETE
    }
  },
  watch: {
    showDetailDialogFlag: function (val) {
      if (val) {
        for (let key in this.validMsg) {
          this.validMsg[key] = ''
        }
      }
    },
    'listGrid.currentData.companyname': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.companynameMsg = ''
      }
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
    isValidationCheck: function () {
      let rtnFlag = true
      if (!this.$util.isNull(this.validMsg.companytelMsg)) {
        return false
      }
      if (!this.$util.isNull(this.validMsg.companynumMsg)) {
        return false
      }
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      let companyname = this.listGrid.currentData.companyname
      let companytype = this.listGrid.currentData.companytype
      if (this.$util.isNull(companyname)) {
        this.validMsg.companynameMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(companytype)) {
        this.validMsg.companytypeMsg = this.labels.inputData
        rtnFlag = false
      }
      return rtnFlag
    },
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'companytypeSelect', domainId: 'COMPANYTYPE'}
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
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/asset/management/company',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      this.$http.get(
        '/episode/api/asset/management/company/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    save: function () {
      if (this.isValidationCheck()) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/asset/management/company/' + this.listGrid.currentData.id : '/episode/api/asset/management/company'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.showDialogFlag = false
            this.listGrid.filters[0].value = '/'
            this.listGrid.filters[0].value = this.searchParam
            this.getRowData(res.data.id)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        } else {
          this.showDetailDialogFlag = false
        }
      }
    },
    del: function (row) {
      let $this = this
      this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
        $this.$http.delete(
          '/episode/api/asset/management/company/' + row.id
        ).then(res => {
          $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
          $this.getListData()
        }).catch(error => {
          $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
          console.log(error)
        })
      })
    },
    // EVENT FN ///////////////////////////////////////////
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.searchType = 'NORMAL'
      this.listGrid.filters[0].value = this.searchParam
    },
    openDetailDialog: function () {
      this.validMsg.companynameMsg = ''
      this.validMsg.companytypeMsg = ''
      this.showDetailDialogFlag = true
      this.$http.get(
        '/episode/api/asset/management/company/companyId?orgId=' + this.orgId
      ).then(res => {
        this.listGrid.currentData = {
          orgId: this.orgId,
          companyId: res.data,
          companytype: 'MNFCTR'
        }
      }).catch(error => {
        console.log(error)
      })
    },
    closeDetailDialog: function () {
      this.showDetailDialogFlag = false
    },
    clickGridRow: function (row) {
      this.showDetailDialogFlag = true
      this.getRowData(row.id)
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      this.$http.get(
        '/episode/api/asset/management/company?searchParam=' + encodeURI(searchParam) + '&searchType=' + this.searchType
      ).then(res => {
        let fileName = 'company'
        let fields = [
          {value: 'companyId', label: this.labels.id},
          {value: 'companyname', label: this.labels.companyname},
          {value: 'companydescription', label: this.labels.description},
          {value: 'companytypenm', label: this.labels.companytype},
          {value: 'companynum', label: this.labels.companynum},
          {value: 'companytel', label: this.labels.companytel},
          {value: 'ownername', label: this.labels.ownername}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    },
    chkCompanyTel: function () {
      let exptext = /^\(?([0-9]{3})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/
      let companytel = this.listGrid.currentData.companytel
      this.validMsg.companytelMsg = ''
      if (!exptext.test(companytel) && !this.$util.isNull(companytel)) {
        this.validMsg.companytelMsg = this.labels.companytelMsg
      }
    },
    chkCompanyNum: function () {
      let exptext = /^\(?([0-9]{3})\)?[-. ]?([0-9]{2})[-. ]?([0-9]{5})$/
      let companynum = this.listGrid.currentData.companynum
      this.validMsg.companynumMsg = ''
      if (!exptext.test(companynum) && !this.$util.isNull(companynum)) {
        this.validMsg.companynumMsg = this.labels.companynumMsg
      }
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.labels = {}
    this.authorities = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
