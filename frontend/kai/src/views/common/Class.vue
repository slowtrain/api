<template>
  <el-dialog :title="labels.classDialogTitle" :visible.sync="showDialogFlag" width=1100px v-on:close="close">
    <b-row>
      <b-col sm="4">
        <Tree
          v-bind:tree="tree"
          ref="tree"
          v-on:clickNode="clickNode"
        />
      </b-col>
      <b-col sm="8">
        <b-row>
          <b-col sm="12">
            <div class="search-component">
              <input class="form-control formdata sm" style="width: 100%;"  v-model="listGrid.filters[0].value" :placeholder="labels.searchA"/>
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
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[6].prop' :label='labels.description'    sortable='custom' ></el-table-column>
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
  name: 'CLASSIFICATION',
  props: ['showDialogFlagProp', 'param'],
  components: {
    'Tree': Tree
  },
  data () {
    return {
      labels: {
        classstructureId: ''
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
        shwoAddIconFn: function (data) {
          return false
        },
        expand: null
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
          prop: ['id', 'orgId', 'parent', 'classstructureId', 'description', 'status'],
          value: ''
        }, {
          prop: 'treeData',
          value: '',
          filterFn: function (data, item) {
            let treeData = item.value
            return (data.hierarchypath.indexOf(treeData.hierarchypath + '/') > -1 || data.classstructureId === treeData.classstructureId || treeData.classstructureId === 'CLASSSTRUCTURE')
          }
        }],
        filteredData: [],
        titles: [{
          prop: 'chk',
          label: 'chk',
          width: '30px'
        }, {
          prop: 'id',
          label: 'id.',
          width: '100px'
        }, {
          prop: 'orgId',
          label: 'orgId',
          width: '100px'
        }, {
          prop: 'parent',
          label: 'parent',
          width: '150px'
        }, {
          prop: 'classstructureId',
          label: 'classstructureId',
          width: '150px'
        }, {
          prop: 'status',
          label: 'status',
          width: '100px'
        }, {
          prop: 'description',
          label: 'description',
          width: '200x'
        }, {
          prop: 'hierarchypath',
          label: 'hierarchypath',
          width: '400px'
        }],
        columns: ['id', 'orgId', 'parent', 'classstructureId', 'description', 'status', 'hierarchypath'],
        columnNames: ['id', 'orgId', 'parent', 'classstructureId', 'description', 'status', 'hierarchypath'],
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
        '/episode/api/system/common/classification?orgId=' + this.orgId + '&object=' + (this.$util.isNull(this.param) ? 'CI' : this.param.object)
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=' + (this.$util.isNull(this.param) ? 'CI' : this.param.object)
      ).then(res => {
        this.tree.data = res.data
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
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
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
