<!--
 Class Name  : LicenseSwcatDialog.vue
 Description : 라이선스 > 관련 카탈로그 > 관련 카탈로그 추가 Dialog
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.16  최영훈  최초 생성
-->
<template>
  <div>
    <el-dialog :title="labels.swcatDialogTitle" :visible.sync="showDialogFlag" width=1550px  v-on:close="close">
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
            <b-col sm="11">
              <div class="search-component">
                <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
              </div>
            </b-col>
            <div>
              <el-popover placement="right" width="500" trigger="click" v-model="visibleSearchDetailFlag">
                <b-row style="padding-left:15px; padding-bottom:10px; font-size:16px; font-weight:bold;">
                  {{labels.searchDetail}}
                </b-row>
                <b-row>
                <div class='form-group col-md-6'>
                  <label for="ciId">{{labels.swcatId}}</label>
                  <input id="ciId" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcatIdD">
                </div>
                <!-- <div class='form-group col-md-6'>
                  <label for="classstructure">{{labels.classstructure}}</label>
                  <div class="search-component sm">
                    <input id="classstructureId" v-model="listGrid.searchParamDetail.classstructureNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
                    <a class="search-button cursor-pointer" v-on:click="openClassDialog">search</a>
                  </div>
                </div> -->
                <div class='form-group col-md-6'>
                  <label for="ciname">{{labels.publisher}}</label>
                  <input id="ciname" class="formdata sm float-right" v-model="listGrid.searchParamDetail.publishernameD"/>
                </div>
                <div class='form-group col-md-6'>
                  <label for="description">{{labels.name}}</label>
                  <input id="description" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcatnameD"/>
                </div>
                <div class='form-group col-md-6'>
                  <label for="itsmciid">{{labels.type}}</label>
                  <input id="itsmciid" class="formdata sm float-right" v-model="listGrid.searchParamDetail.swcattypeD"/>
                </div>
              </b-row>
                <b-row>
                  <div class="col mt-2">
                    <div class='d-flex justify-content-end'>
                      <b-button variant="primary" class="px-3 sm" v-on:click="searchDetailReset">{{labels.reset}}</b-button>
                      <b-button variant="primary" class="px-3 sm" v-on:click="searchDetail">{{labels.search}}</b-button>
                    </div>
                  </div>
                </b-row>
                <el-button slot="reference" style="margin-top:4px !important" :class='searchDetailBtnClass'></el-button>
              </el-popover>
            </div>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables-server ref="listGrid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :pagination-props="{pageSizes: [5, 10, 15] }" :current-page.sync='listGrid.currentPage'>
                <el-table-column :width='listGrid.titles[0].width'>
                  <template slot-scope="props">
                    <el-checkbox v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                  </template>
                </el-table-column>
                <el-table-column :prop='listGrid.titles[1].prop' :label='labels.swcatId'          sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[2].prop' :label='labels.classstructure' sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[3].prop' :label='labels.publisher'        sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[4].prop' :label='labels.name'             sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[5].prop' :label='labels.cattype'          sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[6].prop' :label='labels.version'          sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
              </data-tables-server>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="add" >{{labels.add}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
    <!-- Class Dialog -->
    <Class
      ref="classDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
      v-bind:param="{object: 'LICENSE', expand: {data: {classstructureId: 'SOFTWARE'}}}"
    />
  </div>
</template>
<script>
import Tree from '@/views/common/TreeCom'
import Class from '@/views/common/Class'
export default {
  props: ['labels', 'showDialogFlagProp', 'param'],
  components: {
    'Tree': Tree,
    'Class': Class
  },
  data () {
    return {
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
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['classstructure_id', 'isdomestic', 'publishername', 'swcatname', 'swcatdescription'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'chk',
          width: '30px'
        }, {
          prop: 'swcatId',
          width: '120px'
        }, {
          prop: 'classstructureNm',
          width: '180px'
        }, {
          prop: 'publishername',
          width: '210px'
        }, {
          prop: 'swcatname',
          width: '300px'
        }, {
          prop: 'swcattype',
          width: '140px'
        }, {
          prop: 'swcatversion',
          width: '140px'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        total: 0,
        srchCnd: null,
        selection: [],
        searchParamDetail: {
          swcatIdD: '',
          classstructureD: '',
          classstructureNm: '',
          swcatnameD: '',
          publishernameD: '',
          swcattypeD: ''
        }
      },
      // Global Var
      orgId: '',
      searchType: 'NORMAL',
      searchParam: '',
      ciDialogParam: '',
      showDialogFlag: false,
      visibleSearchDetailFlag: false,
      showClassDialogFlag: false
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.initialize()
        this.searchParam = ''
        this.listGrid.searchParamDetail.swcatIdD = ''
        this.listGrid.searchParamDetail.classstructureD = ''
        this.listGrid.searchParamDetail.classstructureNm = ''
        this.listGrid.searchParamDetail.swcatnameD = ''
        this.listGrid.searchParamDetail.publishernameD = ''
        this.listGrid.searchParamDetail.swcattypeD = ''
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
        this.listGrid.selection = []
        setTimeout(() => {
          this.listGrid.currentPage = 1
          this.listGrid.filters[0].value = '/'
          this.listGrid.filters[0].value = ''
        }, 100)
      }
    }
  },
  computed: {
    searchDetailBtnClass: function () {
      let flag = false
      for (let key in this.listGrid.searchParamDetail) {
        if (!this.$util.isNull(this.listGrid.searchParamDetail[key])) {
          flag = true
        }
      }
      return flag ? 'filter-button-on' : 'filter-button'
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=LICENSE'
      ).then(res => {
        this.tree.data = res.data
        this.tree.expandKeys = [res.data[0].children[0].id]
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 5
      let orderBy = null
      let sort = 'ascending'
      let searchParam = ''
      let treeSearchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        treeSearchParam = srchCnd.filters[1].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        orgId: this.orgId,
        searchParam: searchParam,
        treeSearchParam: treeSearchParam,
        dialogType: 'swcat',
        dialogParam: this.param.swlicenseId,
        swcatIdD: this.listGrid.searchParamDetail.swcatIdD,
        classstructureD: this.listGrid.searchParamDetail.classstructureD,
        swcatnameD: this.listGrid.searchParamDetail.swcatnameD,
        publishernameD: this.listGrid.searchParamDetail.publishernameD,
        swcattypeD: this.listGrid.searchParamDetail.swcattypeD}
      }
      this.$http.get(
        '/episode/api/asset/management/swcat',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    clickNode: function (node) {
      this.searchParam = ''
      this.listGrid.filters[0].value = ''
      if (!this.$util.isNull(node)) {
        let filterVal = (node.data.classstructureId === 'CLASSSTRUCTURE' ? '' : node.data.classstructureId)
        this.listGrid.filters[1].value = filterVal
      }
    },
    filterFocus: function () {
      this.listGrid.filters[0].value = ''
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    searchDetail: function () {
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
    },
    searchDetailReset: function () {
      this.listGrid.searchParamDetail.swcatIdD = ''
      this.listGrid.searchParamDetail.classstructureD = ''
      this.listGrid.searchParamDetail.swcatnameD = ''
      this.listGrid.searchParamDetail.publishernameD = ''
      this.listGrid.searchParamDetail.swcattypeD = ''
      this.listGrid.filters[0].value = '/'
      this.listGrid.filters[0].value = ''
      this.visibleSearchDetailFlag = false
    },
    openClassDialog: function () {
      this.showClassDialogFlag = true
    },
    confirmClass: function (rslt) {
      this.visibleSearchDetailFlag = true
      this.showClassDialogFlag = false
      this.listGrid.searchParamDetail.classstructureD = rslt.classstructureId
      this.listGrid.searchParamDetail.classstructureNm = rslt.classstructureNm
    },
    closeClassDialog: function () {
      this.visibleSearchDetailFlag = true
      this.showClassDialogFlag = false
    },
    selectGridRow: function (select) {
      if (select.chk) {
        let grpCnt = 0
        let targetChk = ''
        this.listGrid.selection.forEach(data => {
          let checkPath = data.hierarchypath
          let targetPath = select.hierarchypath
          if (data.swcattype === 'GROUP') {
            grpCnt++
          }
          if (checkPath.indexOf(targetPath) > -1) {
            this.$util.esmessage(this, 'warn', this.labels.notselect)
            select.chk = false
            return false
          }
          if (targetPath.indexOf(checkPath) > -1) {
            this.$util.esmessage(this, 'warn', this.labels.notselect)
            select.chk = false
            return false
          }
          targetChk = targetChk + ',' + data.swcatId
        })
        if (grpCnt > 0 || select.swcattype === 'GROUP') {
          targetChk = targetChk.substring(1)
          this.$http.get(
            '/episode/api/asset/licensemgmnt/license/chkdupswcat?targetChk=' + targetChk + '&sourceChk=' + select.swcatId
          ).then(res => {
            if (res.data.length > 0) {
              this.$util.esmessage(this, 'warn', this.labels.notselect)
              select.chk = false
              return false
            }
            this.listGrid.selection.push(select)
          }).catch(error => {
            console.log(error)
          })
        } else if (select.chk) {
          this.listGrid.selection.push(select)
        }
      } else {
        this.listGrid.selection.forEach((data, idx) => {
          if (data.id === select.id) {
            this.listGrid.selection.splice(idx)
            return false
          }
        })
      }
    },
    add: function () {
      this.$emit('add', this.listGrid.selection)
    },
    close: function () {
      this.$emit('close')
    }
  },
  created: function () {}
}
</script>
