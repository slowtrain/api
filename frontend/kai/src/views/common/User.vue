<template>
  <el-dialog :title="labels.ciDialogTitle" :visible.sync="showDialogFlag" width=800px v-on:close="close">
    <b-row>
      <b-col>
        <b-row>
          <b-col sm="12">
            <div class="search-component">
              <input class="form-control mt-1" style="width: 100%;" v-model="listGrid.filters[0].value" v-bind:placeholder=labels.search />
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
              <el-table-column :width='listGrid.titles[0].width' >
                <template slot-scope="props">
                  <el-checkbox class='single' v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.username'     sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.userfullname' sortable='custom' ></el-table-column>
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
  name: 'USER',
  props: ['showDialogFlagProp', 'username'],
  data () {
    return {
      labels: {
        title: '',
        username: ''
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
          prop: ['username', 'firstname'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'chk',
          width: '30px'
        }, {
          prop: 'username',
          width: '150px'
        }, {
          prop: 'firstname',
          width: '150px'
        }],
        currentData: {},
        total: 0
      },
      // Global Var
      orgId: '',
      selectedItem: [],
      showDialogFlag: false
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.selectedItem = []
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
        '/episode/api/system/common/user'
      ).then(res => {
        this.listGrid.data = res.data
        if (!this.$util.isNull(this.username)) {
          this.listGrid.filters[0].value = this.username
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
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
