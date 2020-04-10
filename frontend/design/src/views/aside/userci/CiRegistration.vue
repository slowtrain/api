<!--
 File Name  : CiRegistration.vue
 Description : 사이드 바 > 관심장비 등록 팝업
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.13  이정호  최초 생성
-->
<template>
  <el-dialog :title="labels.cirRgistrationTitle" :visible="true" width=1000px :show-close="false">
    <div class="animated fadeIn">
      <b-row>
        <b-col sm="3">
          <Tree
            v-if='tree.showTreeFlag'
            style="height: 350px"
            v-bind:tree="tree"
            ref="tree"
            v-on:clickNode="clickNode"
          />
        </b-col>
        <b-col sm="9">
          <b-row>
            <b-col sm="12">
              <div class="search-component">
                <input class="form-control mt-1" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
              </div>
            </b-col>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables-server ref="listGrid" :table-props='listGrid.tableProps' @selection-change="handleCiSelectionChange" :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :pagination-props="{pageSizes: [5, 10, 15] }" :current-page.sync='listGrid.currentPage'>
                <el-table-column type="selection" width="60" ></el-table-column>
                <el-table-column :prop='listGrid.titles[1].prop' :label='labels.ciId'               sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[2].prop' :label='labels.classstructureId'   sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[3].prop' :label='labels.ciname'             sortable='custom' ></el-table-column>
                <el-table-column :prop='listGrid.titles[4].prop' :label='labels.extaln17'           sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                </data-tables-server>
              </b-col>
            </b-row>
          </b-col>
        </b-row>
        <b-row>
          <div class="col mt-2">
            <div class='d-flex justify-content-end'>
              <b-button variant="primary" class="px-3 sm" v-on:click="addUserCi" >{{labels.save}}</b-button>
              <b-button variant="primary" class="px-3 sm" v-on:click="popupClose">{{labels.close}}</b-button>
            </div>
          </div>
        </b-row>
    </div>
  </el-dialog>
</template>
<script>
import Tree from '@/views/common/TreeCom'
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'USERCI_POPUP',
  components: {
    'Tree': Tree
  },
  data () {
    return {
      labels: {
        description: '',
        ciname: '',
        ciId: '',
        classstructureId: '',
        orgId: '',
        id: '',
        search: '',
        status: '',
        cirRgistrationTitle: '',
        saveMessage: '',
        close: '',
        save: '',
        extaln17: '',
        selectNullMsg: ''
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
          pageSize: 10,
          pageSizes: [10, 20, 30]
        },
        data: [],
        dataBak: [],
        filters: [{
          prop: ['ciId', 'classstructureNm', 'ciname', 'extaln17', 'classstructureId'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'chk',
          width: '20px'
        }, {
          prop: 'ciId',
          width: '120px'
        }, {
          prop: 'classstructureNm',
          width: '150px'
        }, {
          prop: 'ciname',
          width: '150x'
        }, {
          prop: 'extaln17',
          width: '200x'
        }, {
          prop: 'classstructureId',
          width: '120px'
        }],
        total: 0
      },
      userName: '',
      orgId: '',
      searchParam: '',
      treeSearchParam: '',
      ciDialogParam: '',
      showDialogFlag: false,
      ciDialogType: 'userci',
      classObj: 'USERCI',
      ciSelect: []
    }
  },
  methods: {
    initialize: function () {
      let user = this.$store.getters.getUser
      this.userName = user.username
      this.orgId = user.orgId
      this.listGrid.currentPage = 1
      this.searchParam = ''
      this.treeSearchParam = ''
      this.ciSelect = []
      this.$util.setLabels(this.$options.name, this.labels)
      this.tree.selectData = {}
      this.listGrid.filters[0].value = ''
      this.listGrid.filters[1].value = ''
      this.getInitData()
    },
    getInitData: function () {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=' + this.classObj
      ).then(res => {
        this.tree.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function (srchCnd) {
      let page = 1
      let pageSize = 5
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let treeSearchParam = ''
      if (srchCnd !== null && srchCnd !== undefined && srchCnd !== 'undefined') {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value.trim()
        treeSearchParam = encodeURI(srchCnd.filters[1].value)
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        ciDialogType: this.ciDialogType,
        userName: this.userName}
      }
      this.$http.get(
        '/episode/api/asset/resource/ci',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    clickNode: function (node) {
      this.searchParam = ''
      this.listGrid.filters[0].value = ''
      this.treeSearchParam = ''
      if (!this.$util.isNull(node)) {
        let filterVal = (node.data.classstructureId === 'CLASSSTRUCTURE' ? '' : node.data.classstructureId)
        this.listGrid.filters[1].value = filterVal
      }
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.searchParam
    },
    handleCiSelectionChange: function (val) {
      this.ciSelect = val
    },
    addUserCi: function (rows) {
      if (this.ciSelect.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return
      }
      let params = {rows: this.ciSelect}
      this.$http.post(
        '/episode/api/sidebar/userci'
        , params
      ).then(res => {
        eventBus.$emit('getUserCiList')
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.popupClose()
      }).catch(error => {
        console.log(error)
      })
    },
    popupClose: function () {
      this.$emit('ciRegistrationClose')
    }
  },
  mounted () {
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.tree.data = []
    this.$nextTick(function () {
      this.tree.showTreeFlag = false
    })
  }
}
</script>
