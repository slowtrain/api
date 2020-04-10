<!--
 Class Name  : ConversionConverterMgmntDialog.vue
 Description : 변환 Converter 관리
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.21  최영훈  최초 생성
-->
<template>
  <el-dialog :title="labels.convertermgmnt" :visible.sync="showDialogFlag" width=930px  v-on:close="close">
    <b-row>
      <b-col sm="12">
        <b-row>
          <b-col sm="12">
            <div class="comm-btn-box" role="group">
              <b-button variant="primary" class="px-3 sm" v-show="visibleConverterAdd" v-on:click="add">{{labels.new}}</b-button>
            </div>
          </b-col>
        </b-row>
        <p class="grid-line"></p>
        <b-row>
          <b-col sm="11">
            <div class="search-component">
              <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
            </div>
          </b-col>
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
            <data-tables-server ref="listGrid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :pagination-props="{pageSizes: [5, 10, 15] }" :current-page.sync='listGrid.currentPage'>
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.converterId' sortable='custom' :width='listGrid.titles[0].width'>
                <template slot-scope="props">
                  <input class="form-control formdata sm" type="text" v-model="props.row.converterId" v-show="!props.row.hasOwnProperty('id')" v-on:keyup="upperCase(props.$index, props.row.converterId)" v-on:change="changeValue(props.row)" v-bind:maxlength="30"/>
                  <span v-show="props.row.hasOwnProperty('id')">{{props.row.converterId}}</span>
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.originalvalue'  sortable='custom'>
                <template slot-scope="props">
                  <input class="form-control formdata sm" type="text" v-model="props.row.originalvalue" v-show="!props.row.hasOwnProperty('id')" v-on:change="changeValue(props.row)" v-bind:maxlength="200"/>
                  <span v-show="props.row.hasOwnProperty('id')">{{props.row.originalvalue}}</span>
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.convertedvalue' sortable='custom'>
                <template slot-scope="props">
                  <input class="form-control formdata sm" type="text" v-model="props.row.convertedvalue" v-on:change="changeValue(props.row)" v-bind:maxlength="200"/>
                </template>
              </el-table-column>
              <el-table-column align="right" width="80">
                <template slot-scope="props">
                  <el-button  v-on:click="del(props.$index, props.row)" :title="labels.delete" v-show="visibleConverterDelete" style="padding: 0; width:30px; height: 30px;">
                    <img src="../../../../assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables-server>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <b-row>
      <div class="col mt-2">
        <div class='d-flex justify-content-end'>
          <b-button variant="primary" class="px-3 sm" v-show="visibleConverterSave" v-on:click="save">{{labels.save}}</b-button>
          <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
        </div>
      </div>
    </b-row>
  </el-dialog>
</template>
<script>
export default {
  name: 'CONVERSION',
  props: ['showDialogFlagProp', 'labels', 'authorities', 'param'],
  data () {
    return {
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
          prop: [''],
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'converterId',
          width: '200px'
        }, {
          prop: 'originalvalue',
          width: '300px'
        }, {
          prop: 'convertedvalue',
          width: '300px'
        }],
        total: 0,
        srchCnd: {}
      },
      // Global Var
      orgId: '',
      searchParam: '',
      showDialogFlag: false,
      validConverterFlag: false
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.selectedItem = {}
        this.listGrid.currentPage = 1
        this.listGrid.srchCnd = {}
        this.searchParam = ''
        this.listGrid.filters[0].value = '/'
        this.listGrid.filters[0].value = ''
      }
    }
  },
  computed: {
    visibleConverterAdd: function () {
      return this.authorities.CONVERSION_CONVERTER_ADD
    },
    visibleConverterSave: function () {
      return this.authorities.CONVERSION_CONVERTER_SAVE
    },
    visibleConverterDelete: function () {
      return this.authorities.CONVERSION_CONVERTER_DELETE
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    isValidationCheck: function () {
      this.validConverterFlag = false
      let dataList = this.listGrid.data.filter((data, index) => {
        return (data.state === 'ADDED' || data.state === 'CHANGED')
      })
      if (dataList.filter(data => {
        return this.$util.isNull(data.converterId) ||
        this.$util.isNull(data.originalvalue) ||
        this.$util.isNull(data.convertedvalue)
      }).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.inputConverterDataMsg)
        this.validConverterFlag = true
        return []
      }
      return dataList
    },
    upperCase: function (idx, val) {
      this.listGrid.data[idx].converterId = val.toUpperCase()
    },
    // TRNS FN ////////////////////////////////////////////
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 5
      let orderBy = 'id'
      let sort = 'descending'
      let searchParam = ''
      if (srchCnd !== null && srchCnd !== undefined && srchCnd !== 'undefined') {
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
        lang: this.lang,
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/asset/management/conversion/converter',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    chkDup: function (row) {
      if (this.$util.isNull(row.converterId) || this.$util.isNull(row.originalvalue)) {
        return false
      }
      let chkDupList = this.listGrid.data.filter(data => {
        return (data.converterId === row.converterId && data.originalvalue === row.originalvalue)
      })
      if (chkDupList.length > 1) {
        row.converterId = ''
        row.originalvalue = ''
        this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
      }
      let converterId = row.converterId
      let originalvalue = row.originalvalue
      if (!row.hasOwnProperty('id') && !this.$util.isNull(converterId) && !this.$util.isNull(originalvalue)) {
        let params = {params: {
          converterId: converterId,
          originalvalue: originalvalue}
        }
        this.$http.get(
          '/episode/api/asset/management/conversion/converter',
          params
        ).then(res => {
          if (res.data.total > 0) {
            row.converterId = ''
            row.originalvalue = ''
            this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    save: function () {
      let dataList = this.isValidationCheck()
      if (this.validConverterFlag) return
      if (dataList.length > 0) {
        this.$http.post(
          '/episode/api/asset/management/conversion/converter',
          dataList
        ).then(res => {
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.listGrid.filters[0].value = '/'
          this.listGrid.filters[0].value = this.searchParam
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.close()
      }
    },
    // EVENT FN ///////////////////////////////////////////
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    changeValue: function (row) {
      if (!this.$util.isNull(row.id)) {
        row.state = 'CHANGED'
      } else {
        row.state = 'ADDED'
      }
      this.chkDup(row)
    },
    close: function () {
      this.$emit('close')
    },
    add: function () {
      this.listGrid.data.splice(0, 0, {state: 'ADDED'})
    },
    del: function (idx, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/asset/management/conversion/converter/' + row.id).then(res => {
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
          this.listGrid.filters[0].value = '/'
          this.listGrid.filters[0].value = this.searchParam
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.listGrid.data.splice(idx, 1)
      }
    },
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'id'
      let sort = 'ascending'
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
        searchParam: searchParam}
      }
      this.$http.get(
        '/episode/api/asset/management/conversion/converter',
        params
      ).then(res => {
        let fileName = 'converter'
        let fields = [
          {value: 'converterId', label: this.labels.converterId},
          {value: 'originalvalue', label: this.labels.originalvalue},
          {value: 'convertedvalue', label: this.labels.convertedvalue}
        ]
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
