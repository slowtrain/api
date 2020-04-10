<!--
 Class Name  : ConversionConverterDialog.vue
 Description : 변환 Converter 선택
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.21  최영훈  최초 생성
-->
<template>
  <div>
    <el-dialog :title="labels.converterId" :visible.sync="showDialogFlag" width=500px  v-on:close="close">
      <b-row>
        <b-col sm="12">
          <b-row>
            <b-col sm="12">
              <div class="search-component">
                <input class="form-control mt-1" style="width: 100%;" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
              </div>
            </b-col>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables style="width: 100%" layout='table, pagination'
                :table-props='listGrid.tableProps'
                :page-size='listGrid.pageProps.pageSize'
                :data='listGrid.data'
                :filters='listGrid.filters'
                :pagination-props='listGrid.pageProps'
                :current-page.sync='listGrid.currentPage'>
                <el-table-column :width='listGrid.titles[0].width'>
                  <template slot-scope="props">
                    <el-checkbox class='single' v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                  </template>
                </el-table-column>
                <el-table-column :prop='listGrid.titles[1].prop' :label='labels.converterId'    sortable='custom'></el-table-column>
                <el-table-column align="right" width="80">
                  <template  slot-scope="props">
                    <el-button v-on:click="openConverterDialog(props.row)" :title="labels.viewdetaillic" style="padding: 0; width:30px; height: 30px;">
                      <img src="@/assets/img/icons/icon_inventory_search.png" width="60%" />
                    </el-button>
                  </template>
                </el-table-column>
              </data-tables>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="confirm">{{labels.confirm}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
    <el-dialog :title="labels.converterdetail" :visible.sync="showConverterDialogFlag" width=930px  v-on:close="closeConverterDialog">
      <b-row>
        <b-col sm="12">
          <b-row>
            <b-col sm="12">
              <div class="search-component">
                <input class="form-control mt-1" style="width: 100%;" v-model="converterGrid.searchParam" v-on:keyup.enter="enterSearchConverter" v-bind:placeholder=labels.search />
              </div>
            </b-col>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables style="width: 100%" layout='table, pagination'
                :table-props='converterGrid.tableProps'
                :page-size='converterGrid.pageProps.pageSize'
                :data='converterGrid.data'
                :filters='converterGrid.filters'
                :pagination-props='converterGrid.pageProps'
                :current-page.sync='converterGrid.currentPage'>
                <el-table-column :prop='converterGrid.titles[0].prop' :label='labels.converterId'    sortable='custom' :width='converterGrid.titles[0].width'></el-table-column>
                <el-table-column :prop='converterGrid.titles[1].prop' :label='labels.originalvalue'  sortable='custom'></el-table-column>
                <el-table-column :prop='converterGrid.titles[2].prop' :label='labels.convertedvalue' sortable='custom'></el-table-column>
              </data-tables>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="closeConverterDialog">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
  </div>
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
          prop: ['converterId'],
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'chk',
          width: '30px'
        }, {
          prop: 'converterId',
          width: '200px'
        }],
        total: 0,
        searchParam: ''
      },
      converterGrid: {
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
          prop: ['converterId', 'originalvalue', 'convertedvalue'],
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
        searchParam: ''
      },
      selectedItem: {},
      // Global Var
      orgId: '',
      ciDialogParam: '',
      showDialogFlag: false,
      showConverterDialogFlag: false
    }
  },
  watch: {
    showDialogFlagProp: function (val) {
      this.showDialogFlag = val
      if (val) {
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
        this.selectedItem = {}
        this.listGrid.currentPage = 1
        this.listGrid.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.getListData()
      }
    },
    showConverterDialogFlag: function (val) {
      if (val) {
        this.converterGrid.currentPage = 1
        this.converterGrid.searchParam = ''
        this.converterGrid.filters[0].value = ''
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getListData: function (srchCnd) {
      this.$http.get(
        '/episode/api/asset/management/conversion/convertercodelist'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getConverterData: function (converterId) {
      this.$http.get(
        '/episode/api/asset/management/conversion/converter?converterId=' + converterId
      ).then(res => {
        this.converterGrid.data = res.data.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    enterSearch: function () {
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    enterSearchConverter: function () {
      this.converterGrid.filters[0].value = this.converterGrid.searchParam
    },
    selectGridRow: function (row) {
      let chkedList = this.listGrid.data.filter((data) => data.chk === true)
      chkedList.forEach(data => {
        if (data.converterId !== row.converterId) {
          data.chk = false
        }
      })
      this.selectedItem = row
    },
    confirm: function () {
      this.$emit('confirm', this.selectedItem)
    },
    close: function () {
      this.$emit('close')
    },
    openConverterDialog: function (row) {
      this.showConverterDialogFlag = true
      this.getConverterData(row.converterId)
    },
    closeConverterDialog: function () {
      this.showConverterDialogFlag = false
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
