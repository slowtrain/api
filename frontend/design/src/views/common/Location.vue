<template>
  <el-dialog :title="labels.locationDialogTitle" :visible.sync="showDialogFlag" width=900px  v-on:close="close">
    <b-row>
      <b-col sm="3">
        <Tree
          v-bind:tree="tree"
          ref="tree"
          v-on:clickNode="clickNode"
        />
      </b-col>
      <b-col sm="9">
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
            <data-tables style="width: 100%" layout='table, pagination' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps' :current-page.sync='listGrid.currentPage'>
              <el-table-column :width='listGrid.titles[0].width'>
                <template slot-scope="props">
                  <el-checkbox class='single' v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.locationId'       sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.type'             sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.description'      sortable='custom'></el-table-column>
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
import Tree from '@/views/common/TreeCom'
export default {
  name: 'LOCATION',
  props: ['showDialogFlagProp'],
  components: {
    'Tree': Tree
  },
  data () {
    return {
      labels: {
        title: '',
        locationId: ''
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
        shwoAddIconFn: function () {
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
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['locationId', 'description', 'type'],
          value: ''
        }, {
          prop: 'treeData',
          value: '',
          filterFn: function (data, item) {
            let treeData = item.value
            return (data.hierarchypath.indexOf(treeData.hierarchypath + '/') > -1 || data.locationId === treeData.locationId || treeData.locationId === 'LOCATION')
          }
        }],
        filteredData: [],
        titles: [{
          prop: 'chk',
          label: 'chk',
          width: '30px'
        }, {
          prop: 'locationId',
          label: 'locationId',
          width: '100px'
        }, {
          prop: 'type',
          label: 'type',
          width: '100px'
        }, {
          prop: 'description',
          label: 'description',
          width: '200x'
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
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
        this.listGrid.currentPage = 1
        this.tree.selectData = {}
        this.listGrid.filters[0].value = ''
        this.listGrid.filters[1].value = ''
        this.initialize()
        this.getTreeData()
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
        '/episode/api/system/common/location?orgId=' + this.orgId
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    clickNode: function (node) {
      if (!this.$util.isNull(node)) {
        this.listGrid.filters[0].value = ''
        this.listGrid.filters[1].value = node.data
      }
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
  created: function () {}
}
</script>
