<!--
 Class Name  : Domain.vue
 Description : 코드체계
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
          <b-row>
            <div class="col">
              <div class="comm-btn-box" role="group">
                <b-button variant="primary" class="px-3 sm" v-on:click="add" v-show="visibleAdd">{{labels.new}}</b-button>
              </div>
            </div>
          </b-row>
          <p class="grid-line"></p>
          <b-row>
            <div class='col-md-4' style="padding-top:10px">
              <div class="search-component sm">
                <input class="form-control formdata sm" style="width: 100%;" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder=labels.search />
              </div>
            </div>
            <div class='col-md-8'>
              <div class="float-right mt-1" role="group">
                <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                  <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                </el-button>
              </div>
            </div>
          </b-row>
          <!-- ESDOMAIN -->
          <b-row>
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables ref="esdomainTable" layout='table, pagination' :current-page.sync='listGrid.currentPage' :table-props='listGrid.tableProps' :data='listGrid.data' :filters='listGrid.filters' :page-size="10" :pagination-props='listGrid.pageProps' @filtered-data="handleFilteredData">
                <el-table-column prop="id"          :label="labels.id"          width="200" :sortable="true"></el-table-column>
                <el-table-column prop="domainId"    :label="labels.domainId"    width="200" :sortable="true"></el-table-column>
                <el-table-column prop="description" :label="labels.description" width="*"   :sortable="true"></el-table-column>
                <el-table-column prop="createDt"    :label="labels.createDt"    width="200" :sortable="true">
                  <template slot-scope="scope">
                    {{$util.dateTimeWithTimeZone(scope.row.createDt)}}
                  </template>
                </el-table-column>
                <el-table-column prop="createNm"    :label="labels.createId"    width="150" :sortable="true"></el-table-column>
                <el-table-column align="right" width="100" >
                  <template slot-scope="props">
                    <el-button v-show="props.row.hasOwnProperty('id')" v-on:click="openDomainDialog(props.row)" :title="labels.detail" style="padding:0; width:30px; height:30px;">
                      <img src="../../../assets/img/icons/icon_edit.png" width="60%" />
                    </el-button>
                    <el-button  v-on:click="del(props.$index, props.row)" :title="labels.delete" v-show="visibleDelete" style="padding:0; width:30px; height:30px;">
                      <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                    </el-button>
                  </template>
                </el-table-column>
              </data-tables>
            </div>
          </b-row>
          <!-- ES DOMAIN -->
          <el-dialog :title="labels.title" :visible.sync="showDialogFlag" width="70%">
            <b-row>
              <div class="form-group col-md-6">
                <label for="domain_id">{{labels.domainId}}<span class="require-mark">*</span></label>
                <input id="domain_id" class="formdata sm float-right" style="width: 100%;" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.domainId" v-on:change="changeValue(listGrid.currentData)" :disabled="disabledDomainId"/>
                <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.domainIdMsg)" v-text="validMsg.domainIdMsg"></p>
              </div>
              <div class="form-group col-md-6">
                <label for="domain_description">{{labels.description}}<span class="require-mark">*</span></label>
                <input id="domain_description" class="formdata sm float-right" style="width: 100%;" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.description" v-on:change="changeValue(listGrid.currentData)"/>
                <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
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
             <!-- DOMAIN -->
            <p style="border: thin solid #dbdbe0; margin-top:15px;"></p>
            <b-row>
              <div class='form-group col-md-12'>
                <div class="float-right mt-2" role="group">
                  <b-button variant="primary" class="px-3 sm" v-on:click="addDomain" v-show="visibleAddDetail">{{labels.new}}</b-button>
                </div>
              </div>
            </b-row>
            <b-row>
              <div class='form-group col-md-12'>
                <p class="grid-line"></p>
                <data-tables v-if="listGridFlag" ref="domainTable" layout='table, pagination' :table-props='detailGrid.tableProps' :data='detailGrid.data' :filters='detailGrid.filters' :page-size="10" :pagination-props='detailGrid.pageProps'>
                  <el-table-column prop="id" :label="labels.id" width="80"></el-table-column>
                  <el-table-column prop="domainId" :label="labels.domainId" width="150"></el-table-column>
                  <el-table-column  label="KEY" width="150">
                    <template slot-scope="props">
                      <input class="form-control formdata sm" style="width: 100%;"  type="text" maxlength="20" v-model="props.row.key" v-if="!props.row.hasOwnProperty('id')" v-on:blur="blurKey(props.row)"/>
                      <span v-else>{{props.row.key}}</span>
                      </template>
                  </el-table-column>
                  <el-table-column  label="VALUE" width="150">
                    <template slot-scope="props">
                      <input class="form-control formdata sm" style="width: 100%;"  type="text" maxlength="20" v-model="props.row.value" v-on:change="changeValue(props.row)"/>
                    </template>
                  </el-table-column>
                  <el-table-column prop="seq" label="SEQ" width="80">
                    <template slot-scope="props">
                      <input class="form-control formdata sm" style="width: 100%;"  type="text" maxlength="20" v-model="props.row.seq" v-on:change="changeValue(props.row)"/>
                    </template>
                  </el-table-column>
                  <el-table-column  :label="labels.description" width="*">
                    <template slot-scope="props">
                      <input class="form-control formdata sm" style="width: 100%;"  type="text" maxlength="20" v-model="props.row.description" v-on:change="changeValue(props.row)"/>
                    </template>
                  </el-table-column>
                  <el-table-column prop="createDt" :label="labels.createDt" width="200">
                    <template slot-scope="scope">
                      {{$util.dateTimeWithTimeZone(scope.row.createDt)}}
                    </template>
                  </el-table-column>
                  <el-table-column prop="createId" :label="labels.createId"  width="150"></el-table-column>
                  <el-table-column align="right" width="80">
                    <template slot-scope="props">
                      <el-button  v-on:click="delDomain(props.$index, props.row)" :title="labels.delete" v-show="visibleDelete" style="padding: 0; width:30px; height: 30px;">
                        <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                      </el-button>
                    </template>
                  </el-table-column>
                </data-tables>
              </div>
            </b-row>
            <span slot="footer" class="dialog-footer">
              <b-button variant="primary" class="px-3 sm" v-on:click="save" v-show="visibleSaveDetail">{{labels.save}}</b-button>
              <b-button variant="primary" class="px-3 sm" v-on:click="closeDomainDialog">{{labels.close}}</b-button>
            </span>
          </el-dialog>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>
<script>
export default {
  name: 'DOMAIN',
  data () {
    return {
      labels: {
        title: '',
        new: '',
        delete: '',
        list: '',
        detail: '',
        save: '',
        close: '',
        fail: '',
        domainIdMessage: '',
        descriptiondMessage: '',
        seqMessage: '',
        valueMessage: '',
        childDataExists: '',
        domainIdExists: '',
        keyExists: '',
        id: '',
        domainId: '',
        description: '',
        createDt: '',
        createId: '',
        updateDt: '',
        updateId: '',
        deleteMessage: '',
        saveMessage: '',
        search: '',
        keyMessage: '',
        excelDownload: '',
        valuelengthMessage: '',
        keylengthMessage: '',
        domainIdlengthMessage: ''
      },
      listGrid: {
        currentPage: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        pageProps: {
          pageSizes: [10, 20, 30]
        },
        rowIndex: -1,
        data: [],
        filters: [
          { prop: ['domainId'], value: '' }
        ],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'id', width: '100px' },
          { prop: 'domainId', label: 'domainId', width: '100px' },
          { prop: 'description', label: 'description', width: '100px' },
          { prop: 'createDt', label: 'createDt', width: '100px' },
          { prop: 'createNm', label: 'createId', width: '100px' },
          { prop: 'updateDt', label: 'updateDt', width: '100px' },
          { prop: 'updateNm', label: 'updateId', width: '100px' }
        ],
        columns: ['id', 'domainId', 'description', 'createDt', 'createId', 'updateDt', 'updateId'],
        columnNames: ['id', 'domainId', 'description', 'createDt', 'createId', 'updateDt', 'updateId'],
        currentData: {},
        searchParam: ''
      },
      detailGrid: {
        currentPage: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        pageProps: {
          pageSizes: [10, 20, 30]
        },
        rowIndex: -1,
        data: [],
        filters: [
          { prop: ['description'], value: '' }
        ],
        filteredData: [],
        titles: [
          { prop: 'id', label: 'id', width: '50px' },
          { prop: 'domainId', label: 'domainId', width: '100px' },
          { prop: 'key', label: 'key', width: '100px' },
          { prop: 'value', label: 'value', width: '100px' },
          { prop: 'seq', label: 'seq', width: '100px' },
          { prop: 'description', label: 'description', width: '50px' },
          { prop: 'createDt', label: 'createDt', width: '100px' },
          { prop: 'createNm', label: 'createId', width: '100px' },
          { prop: 'updateDt', label: 'updateDt', width: '100px' },
          { prop: 'updateNm', label: 'updateId', width: '100px' }
        ],
        currentData: {}
      },
      authorities: {
        DOMAIN_ADD: false,
        DOMAIN_SAVE: false,
        DOMAIN_DELETE: false,
        DOMAIN_DETAIL_ADD: false,
        DOMAIN_DETAIL_SAVE: false,
        DOMAIN_DETAIL_DELETE: false
      },
      validMsg: {
        domainIdMsg: '',
        descriptionMsg: ''
      },
      listGridFlag: false,
      showDialogFlag: false,
      validDomainFlag: false
    }
  },
  watch: {
    'listGrid.currentData.domainId': function (val) {
      if (!this.listGrid.currentData.hasOwnProperty('id')) {
        if (this.listGrid.data.filter(esdomain => esdomain.domainId === val).length > 0) {
          this.validMsg.domainIdMsg = this.labels.domainIdExists
        } else {
          this.validMsg.domainIdMsg = ''
        }
      }
    },
    'listGrid.currentData.description': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.descriptionMsg = ''
      }
    },
    showDialogFlag: function (val) {
      this.validMsg.domainIdMsg = ''
      this.validMsg.descriptionMsg = ''
    }
  },
  computed: {
    disabledDomainId: function () {
      return this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAdd: function () {
      return this.authorities.DOMAIN_ADD
    },
    visibleSave: function () {
      return this.authorities.DOMAIN_SAVE
    },
    visibleDelete: function () {
      return this.authorities.DOMAIN_DELETE
    },
    visibleAddDetail: function () {
      return this.authorities.DOMAIN_DETAIL_ADD
    },
    visibleSaveDetail: function () {
      return this.authorities.DOMAIN_DETAIL_SAVE
    },
    visibleDeleteDetail: function () {
      return this.authorities.DOMAIN_DETAIL_DELETE
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels, this.listGrid.titles)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.getListData()
      this.listGridFlag = true
    },
    getListData: function () {
      this.$http.get('/episode/api/system/esdomain').then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getDomainData: function (id) {
      this.$http.get('/episode/api/system/esdomain/' + id + '/domain').then(res => {
        this.detailGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    add: function () {
      this.showDialogFlag = true
      this.listGrid.currentData = {
        state: 'ADDED'
      }
      this.detailGrid.data = []
    },
    isValidationCheck: function () {
      let rtnFlag = true
      if (!this.$util.isNull(this.validMsg.domainIdMsg)) {
        rtnFlag = false
      }
      this.validMsg.descriptionMsg = ''
      if (this.$util.isNull(this.listGrid.currentData.domainId)) {
        this.validMsg.domainIdMsg = this.labels.domainIdMessage
        rtnFlag = false
      }
      if (this.$util.isNull(this.listGrid.currentData.description)) {
        this.validMsg.descriptionMsg = this.labels.descriptiondMessage
        rtnFlag = false
      }
      return rtnFlag
    },
    save: function () {
      if (this.isValidationCheck()) {
        let domainList = this.saveDomain()
        if (this.validDomainFlag) return
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/esdomain/' + this.listGrid.currentData.id : '/episode/api/system/esdomain'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || domainList.length > 0) {
          this.listGrid.currentData.domainList = domainList
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.getListData()
            this.listGrid.currentData = res.data
            this.getDomainData(res.data.id)
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }
    },
    del: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.get('/episode/api/system/esdomain/' + row.id + '/domain').then(res => {
          if (res.data.length > 0) {
            this.$util.esmessage(this, 'warn', this.labels.childDataExists)
          } else {
            this.$http.delete('/episode/api/system/esdomain/' + row.id).then(res => {
              this.getListData()
              this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
            }).catch(error => {
              this.$util.esmessage(this, 'warn', this.labels.fail)
              console.log(error)
            })
          }
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.fail)
          console.log(error)
        })
      } else {
        this.listGrid.data.splice(index, 1)
      }
    },
    addDomain: function () {
      this.detailGrid.data.splice(0, 0, {
        domainId: this.listGrid.currentData.domainId, state: 'ADDED', seq: 0
      })
    },
    saveDomain: function () {
      this.validDomainFlag = false
      let target = this.detailGrid.data.filter(data => data.state === 'ADDED' || data.state === 'CHANGED')
      for (let i = 0; i < target.length; i++) {
        if (this.$util.isNull(target[i].key)) {
          this.$util.esmessage(this, 'warn', this.labels.keyMessage)
          this.validDomainFlag = true
          return []
        }
        if (this.$util.isNull(target[i].value)) {
          this.$util.esmessage(this, 'warn', this.labels.valueMessage)
          this.validDomainFlag = true
          return []
        }
        if (this.$util.isNull(target[i].description)) {
          this.$util.esmessage(this, 'warn', this.labels.descriptiondMessage)
          this.validDomainFlag = true
          return []
        }
        if (this.$util.isNull(target[i].seq)) {
          this.$util.esmessage(this, 'warn', this.labels.seqMessage)
          this.validDomainFlag = true
          return []
        }
      }
      return target
    },
    delDomain: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/system/esdomain/domain/' + row.id).then(res => {
          this.detailGrid.data.splice(index, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.fail)
          console.log(error)
        })
      } else {
        this.detailGrid.data.splice(index, 1)
      }
    },
    openDomainDialog: function (currentRow) {
      this.showDialogFlag = true
      this.listGrid.currentData = currentRow
      this.getDomainData(currentRow.id)
    },
    closeDomainDialog: function () {
      this.showDialogFlag = false
    },
    exportFilter: function () {
      let fileName = 'domain'
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'domainId', label: this.labels.domainId},
        {value: 'description', label: this.labels.description},
        {value: 'createNm', label: this.labels.createId},
        {value: 'createDt', label: this.labels.createDt}
      ]
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    },
    blurKey: function (row) {
      if (this.detailGrid.data.filter(domain => domain.key === row.key).length > 1) {
        delete row.key
        this.$util.esmessage(this, 'warn', this.labels.keyExists)
      }
    },
    changeValue: function (row) {
      if (row.hasOwnProperty('id')) {
        row.state = 'CHANGED'
      } else {
        row.state = 'ADDED'
      }
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
