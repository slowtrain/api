<!--
 Class Name  : AppSystem.vue
 Description : 서비스 > 어플리케이션 손실비용 > 어플리케이션 시스템 팝업
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.25  이정호  최초 생성
-->
<template>
  <el-dialog :title="labels.dialogTitle" :visible.sync="showDialogFlag" width=700px v-on:close="close">
    <b-row>
      <b-col>
        <b-row>
          <b-col sm="12">
            <div class="search-component">
              <input class="form-control mt-1" v-on:keyup.enter="setFilters" v-model="search" v-bind:placeholder=labels.search />
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="12">
            <p class="grid-line"></p>
            <data-tables style="width: 100%" layout='table, pagination'
              ref='test'
              v-on:selection-change="handleCiSelectionChange"
              :table-props='listGrid.tableProps'
              :page-size='listGrid.pageProps.pageSize'
              :data='listGrid.data'
              :filters='listGrid.filters'
              :pagination-props='listGrid.pageProps'>
              <el-table-column type="selection" width="60"></el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.ciId'              sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.classstructureId'  sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.ciname'            sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.description'       sortable='custom' ></el-table-column>
            </data-tables>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <b-row>
      <div class="col mt-2">
        <div class='d-flex justify-content-end'>
          <b-button variant="primary" class="px-3 sm" v-on:click="confirm" >{{labels.confirm}}</b-button>
          <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
        </div>
      </div>
    </b-row>
  </el-dialog>
</template>
<script>
export default {
  name: 'APPSYSTEM',
  props: ['showDialogFlagProp', 'data', 'labels'],
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
          pageSizes: [10, 15, 20]
        },
        data: [],
        filters: [{
          prop: ['ciId', 'classstructureNm', 'ciname', 'description', 'classstructureId'],
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'chk',
          label: 'chk',
          width: '30px'
        }, {
          prop: 'ciId',
          width: '150px'
        }, {
          prop: 'classstructureNm',
          width: '150px'
        }, {
          prop: 'ciname',
          width: '150px'
        }, {
          prop: 'description',
          width: '200'
        }, {
          prop: 'classstructureId',
          width: '100'
        }],
        currentData: {},
        total: 0
      },
      orgId: '',
      showDialogFlag: false,
      search: '',
      ciSelect: []
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      setTimeout(() => {
        this.$refs.test.$refs.elTable.clearSelection()
      }, 200)
    },
    'data': function () {
      this.listGrid.data = this.data
    }
  },
  methods: {
    initialize: function () {
    },
    handleCiSelectionChange: function (val) {
      this.ciSelect = val
    },
    destroy: function () {
      this.listGrid.filters[0].value = ''
      this.search = ''
    },
    setFilters: function () {
      this.listGrid.filters[0].value = this.search
    },
    confirm: function () {
      this.$emit('confirm', this.ciSelect)
    },
    close: function () {
      this.$emit('close')
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
