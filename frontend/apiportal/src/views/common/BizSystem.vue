<!--
 Class Name  : BizSystem.vue
 Description : E2E > 조회 > 비지니스 시스템 팝업
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.23  이정호  최초 생성
-->
<template>
  <el-dialog :title="labels.title" :visible.sync="showDialogFlag" width=700px v-on:close="close">
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
              :table-props='listGrid.tableProps'
              :page-size='listGrid.pageProps.pageSize'
              :data='listGrid.data'
              :filters='listGrid.filters'
              :pagination-props='listGrid.pageProps'>
              <el-table-column :width='listGrid.titles[0].width' >
                <template slot-scope="props">
                  <el-checkbox class='single' v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.ciname'           sortable='custom'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.description'            sortable='custom'></el-table-column>
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
  name: 'BIZSYSTEM_POPUP',
  props: ['showDialogFlagProp'],
  data () {
    return {
      labels: {
        title: '',
        classstructureId: '',
        ciname: '',
        ciId: '',
        orgId: '',
        confirm: '',
        close: ''
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
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['ciId', 'classstructureNm', 'ciname', 'description', 'classstructureId'],
          value: ''
        }],
        filteredData: [],
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
      selectedItem: [],
      showDialogFlag: false,
      rpaSelect: [],
      search: ''
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
        this.getListData()
      }
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.orgId = this.$store.getters.getUser.orgId
    },
    destroy: function () {
      this.listGrid.filters[0].value = ''
      this.search = ''
    },
    setFilters: function () {
      this.listGrid.filters[0].value = this.search
    },
    getListData: function () {
      this.listGrid.data = []
      this.$http.get(
        '/episode/api/e2e/biz'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    selectGridRow: function (row) {
      let chkedList = this.listGrid.data.filter((data) => data.chk === true)
      chkedList.forEach(data => {
        if (data.id !== row.id) {
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
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
