<!--
 Class Name  : LicenseUseServerDialog.vue
 Description : 라이선스사용 > 사용서버 Dialog
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.30  최영훈  최초 생성
-->
<template>
  <el-dialog :title="labels.useServerDialogTitle" :visible.sync="showDialogFlag" width=1000px v-on:close="close">
    <b-row>
      <b-col>
        <b-row>
          <b-col sm="12">
            <div class="search-component">
              <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="12">
            <p class="grid-line"></p>
            <data-tables style="width: 100%" layout='table, pagination' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps' :current-page.sync='listGrid.currentPage'>
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.ciId'         sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.class'        sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'         sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.description'  sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.citype'       sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.manager'      sortable='custom' ></el-table-column>
              <el-table-column align="right" width="80">
                <template  slot-scope="props">
                  <el-button v-on:click="moveCI(props.row)" :title="labels.moveci" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_search_login.png" width="60%" />
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
          <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
        </div>
      </div>
    </b-row>
  </el-dialog>
</template>
<script>
export default {
  props: ['labels', 'showDialogFlagProp', 'param'],
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
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['ciId', 'classstructureNm', 'ciname', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [{
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
          width: '150px'
        }, {
          prop: 'type',
          width: '150px'
        }, {
          prop: 'managerNm',
          width: '150px'
        }],
        currentData: {},
        total: 0
      },
      // Global Var
      orgId: '',
      selectedItem: [],
      showDialogFlag: false,
      searchParam: ''
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.listGrid.currentPage = 1
        this.getListData()
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getListData: function () {
      this.$http.get(
        '/episode/api/asset/licensemgmnt/licenseuse/serverlist?orgId=' + this.orgId + '&swcatId=' + this.param.swcatId
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    close: function () {
      this.$emit('close')
    },
    moveCI: function (row) {
      this.$util.move(this, 'CI', row.id)
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
