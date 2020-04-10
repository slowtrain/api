<!--
 Class Name  : CIDetail.vue
 Description : IT 자원 > 상세
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
   <CIDetailDefault
      ref="ciDetailDefault"
      v-if="(activeTab !== 'list') && this.activeTabFlag.relationInfos"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class='col'>
        <el-tabs  v-if="(activeTab !== 'list') && this.activeTabFlag.relationInfos" v-model="activeTabDetail">
          <!-- Cpu -->
          <el-tab-pane  name="cpu" :label='labels.cpu' v-if="showPartTab()">
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'cpu')" layout='table, pagination' :table-props='relationTabGrid.tableProps' :data='fltrRelation(relationTabGrid.data, {fltrCol: "classstructureId", fltrStr: "CPU"})' :filters='relationTabGrid.filters' :page-size='relationTabGrid.pageProps.pageSize' :pagination-props='relationTabGrid.pageProps'>
                <el-table-column :prop='"ciId"'             :label='labels.ciId'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"classstructureId"' :label='labels.classstructureId'  width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"ciname"'           :label='labels.name'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln1"'          :label='labels.CPUINDEX'          width='130' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln2"'          :label='labels.CPUTYPE'           sortable='custom'></el-table-column>
                <el-table-column :prop='"extnum1"'          :label='labels.CPUSPEED'          width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extnum3"'          :label='labels.CPUCORETOT'        width='110' sortable='custom'></el-table-column>
                <el-table-column :prop='"extnum4"'          :label='labels.CPULOGINUM'        width='150' sortable='custom'></el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
          <!-- Ip -->
          <el-tab-pane  name="ip" :label='labels.ip' v-if="showPartTab()">
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'ip')" layout='table, pagination' :table-props='relationTabGrid.tableProps' :data='fltrRelation(relationTabGrid.data, {fltrCol: "classstructureId", fltrStr: "IPADDRESS"})' :filters='relationTabGrid.filters' :page-size='relationTabGrid.pageProps.pageSize' :pagination-props='relationTabGrid.pageProps'>
                <el-table-column :prop='"ciId"'             :label='labels.ciId'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"classstructureId"' :label='labels.classstructureId'  width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"ciname"'           :label='labels.name'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln1"'          :label='labels.IPADDRTYPE'        width='140' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln2"'          :label='labels.IPADDRSUBNET'      width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln3"'          :label='labels.IPADDRGATEWAY'     width='140' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln4"'          :label='labels.IPADDRDNSSERVER'   width='150' sortable='custom'></el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
          <!-- File Sys -->
          <el-tab-pane  name="filesys" :label='labels.filesys' v-if="showPartTab()">
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'filesys')" layout='table, pagination' :table-props='relationTabGrid.tableProps' :data='fltrRelation(relationTabGrid.data, {fltrCol: "classstructureId", fltrStr: "FILESYSTEM"})' :filters='relationTabGrid.filters' :page-size='relationTabGrid.pageProps.pageSize' :pagination-props='relationTabGrid.pageProps'>
                <el-table-column :prop='"ciId"'             :label='labels.ciId'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"classstructureId"' :label='labels.classstructureId'  width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"ciname"'           :label='labels.name'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln1"'          :label='labels.FSTYPE'            width='160' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln2"'          :label='labels.MOUNTPOINT'        width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extnum1"'          :label='labels.FSSIZEBYTES'       width='160' sortable='custom'></el-table-column>
                <el-table-column :prop='"extnum2"'          :label='labels.FSAVAILBYTES'      width='170' sortable='custom'></el-table-column>
                <el-table-column :prop='"extnum3"'          :label='labels.FSUSEDBYTES'       width='170' sortable='custom'></el-table-column>
                <el-table-column :prop='"extnum4"'          :label='labels.FSUSEDPCT'         width='190' sortable='custom'></el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
          <!-- Application -->
          <el-tab-pane  name="application" :label='labels.application' v-if="showPartTab()">
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'application')" layout='table, pagination' :table-props='relationTabGrid.tableProps' :data='fltrRelation(relationTabGrid.data, {fltrCol: "relationname", fltrStr: "RUN"})' :filters='relationTabGrid.filters' :page-size='relationTabGrid.pageProps.pageSize' :pagination-props='relationTabGrid.pageProps'>
                <el-table-column :prop='"ciId"'             :label='labels.ciId'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"classstructureId"' :label='labels.classstructureId'  width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"ciname"'           :label='labels.name'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln1"'          :label='labels.APPTYPE'           width='170' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln2"'          :label='labels.HOSTNAME'          width='130' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln3"'          :label='labels.IPADDRESS'         width='130' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln4"'          :label='labels.APPPRODNAME'       width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln5"'          :label='labels.APPPRODVERSION'    width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln6"'          :label='labels.MANUFACTURER'      width='200' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln7"'          :label='labels.APPPORT'           width='230' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln8"'          :label='labels.APPPORTLIST'       width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln9"'          :label='labels.HOMEDIR'           width='200' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln10"'         :label='labels.DBSID'             width='200' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln11"'         :label='labels.APPPRODVERDTL'     width='200' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln18"'         :label='labels.ITSMCIID'          width='200' sortable='custom'></el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
          <!-- Vp -->
          <el-tab-pane  name="vp" :label='labels.vp' v-if="showPartTab()">
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'vp')" layout='table, pagination' :table-props='relationTabGrid.tableProps' :data='fltrRelation(relationTabGrid.data, {fltrCol: "relationname", fltrStr: "VIRTUALIZE"})' :filters='relationTabGrid.filters' :page-size='relationTabGrid.pageProps.pageSize' :pagination-props='relationTabGrid.pageProps'>
                <el-table-column :prop='"ciId"'             :label='labels.ciId'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"classstructureId"' :label='labels.classstructureId'  width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"ciname"'           :label='labels.name'              width='150' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln1"'          :label='labels.ARCHITECTURE'      width='170' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln2"'          :label='labels.SERIALNUMBER'      width='130' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln3"'          :label='labels.MANUFACTURER'      width='100' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln4"'          :label='labels.MODEL'             width='90'  sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln5"'          :label='labels.HOSTNAME'          width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln6"'          :label='labels.DNSNAME'           width='200' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln7"'          :label='labels.OSNAME'            width='230' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln8"'          :label='labels.OSVERSION'         width='180' sortable='custom'></el-table-column>
                <el-table-column :prop='"extaln9"'          :label='labels.OSBIT'             width='200' sortable='custom'></el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
          <!-- Relation Sw -->
          <el-tab-pane  name="sw" :label='labels.sw' v-if="showPartTab()">
            <b-row>
              <b-col sm="6">
                <div class="search-component">
                  <input class="form-control mt-1" style="width: 80%;" v-model="relationSwGrid.searchParam" v-on:keyup.enter="enterSearchRelationSw" v-bind:placeholder=labels.search />
                </div>
              </b-col>
            </b-row>
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'sw')" layout='table, pagination' :table-props='relationSwGrid.tableProps' :data='relationSwGrid.data' :filters='relationSwGrid.filters' :page-size='relationSwGrid.pageProps.pageSize' :pagination-props='relationSwGrid.pageProps'>
                <el-table-column :prop='relationSwGrid.titles[0].prop' :label='labels.swClass'          sortable='custom' :width='relationSwGrid.titles[0].width'></el-table-column>
                <el-table-column :prop='relationSwGrid.titles[1].prop' :label='labels.publisher'        sortable='custom' :width='relationSwGrid.titles[1].width'></el-table-column>
                <el-table-column :prop='relationSwGrid.titles[2].prop' :label='labels.name'             sortable='custom' :width='relationSwGrid.titles[2].width'></el-table-column>
                <el-table-column :prop='relationSwGrid.titles[3].prop' :label='labels.version'          sortable='custom' :width='relationSwGrid.titles[3].width'></el-table-column>
                <el-table-column :prop='relationSwGrid.titles[4].prop' :label='labels.detailedversion'  sortable='custom' :width='relationSwGrid.titles[4].width'></el-table-column>
                <el-table-column :prop='relationSwGrid.titles[5].prop' :label='labels.discoverypath'    sortable='custom' :width='relationSwGrid.titles[5].width'></el-table-column>
                <el-table-column :prop='relationSwGrid.titles[6].prop' :label='labels.startDt'          sortable='custom' :width='relationSwGrid.titles[6].width'>
                  <template  slot-scope="props">
                  {{$util.dateTimeWithTimeZone(props.row.startDt)}}
                  </template>
                </el-table-column>
                <el-table-column :prop='relationSwGrid.titles[7].prop' :label='labels.endDt'      sortable='custom' :width='relationSwGrid.titles[7].width'>
                  <template  slot-scope="props">
                  {{props.row.endDt === '9999-12-31T23:59:59.997+0000' ? 'N/A' : $util.dateTimeWithTimeZone(props.row.endDt)}}
                  </template>
                </el-table-column>
                <el-table-column :prop='relationSwGrid.titles[8].prop' :label='labels.islicnouse' sortable='custom' :width='relationSwGrid.titles[8].width'>
                  <template  slot-scope="props">
                    <el-switch v-model="props.row.islicnouse" class="formdata sm" v-on:change="changeValue(props.row)"></el-switch>
                  </template>
                </el-table-column>
                <el-table-column type="expand" :label='labels.licnusecause' align="center" :width='relationSwGrid.titles[9].width'>
                  <template  slot-scope="props">
                    <input id="licnusecause" v-model="props.row.licnusecause" class="formdata sm" autocomplete="off" v-on:change="changeValue(props.row)"/>
                  </template>
                </el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
          <!-- ITSM CI -->
          <el-tab-pane  name="itsm" :label='"ITSM CI"' v-if="!$util.isNull(itsmData.id)">
            <b-row>
              <div class="form-group col-md-2">
                <label for="ciTypeNm">{{labels.classstructureId}}</label>
                <div class="search-component sm">
                  <input id="ciTypeNm" v-model="itsmData.ciTypeNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
                </div>
              </div>
              <div class="form-group col-md-2">
                <label for="kaiCiid">ITSM CIID</label>
                <div class="search-component sm">
                  <input id="kaiCiid" v-model="itsmData.kaiCiid" class="formdata sm" autocomplete="off" disabled="disabled"/>
                </div>
              </div>
              <div class="form-group col-md-4">
                <label for="ciName">{{labels.name}}</label>
                <div class="search-component sm">
                  <input id="ciName" v-model="itsmData.ciName" class="formdata sm" autocomplete="off" disabled="disabled"/>
                </div>
              </div>
              <div class="form-group col-md-2">
                <label for="usestatus">{{labels.status}}</label>
                <div class="search-component sm">
                  <input id="usestatus" v-model="itsmData.usestatus" class="formdata sm" autocomplete="off" disabled="disabled"/>
                </div>
              </div>
            </b-row>
            <b-row>
              <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
            </b-row>
            <b-row>
              <div class="col" style="padding-top:10px; padding-bottom:20px; padding-left:20px;">
                <a class="title-box">ITSM CI SPEC</a>
              </div>
            </b-row>
            <b-row>
              <b-col sm="6">
                <div class="search-component">
                  <input class="form-control mt-1" style="width: 80%;" v-model="itsmSpecGrid.searchParam" v-on:keyup.enter="enterSearchItsmSpec" v-bind:placeholder=labels.search />
                </div>
              </b-col>
            </b-row>
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables border layout='table, pagination' :table-props='itsmSpecGrid.tableProps' :data='itsmSpecGrid.data' :filters='itsmSpecGrid.filters' :page-size='itsmSpecGrid.pageProps.pageSize' :pagination-props='itsmSpecGrid.pageProps'>
                <el-table-column :prop='"ciAttrName"'      :label='labels.item'   sortable='custom'></el-table-column>
                <el-table-column :prop='"ciAttrValueLast"' :label='labels.value'  sortable='custom'></el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
          <!-- DOWNTIME -->
          <el-tab-pane  name="downtime" :label='"DOWNTIME"' v-if="downtimeGrid.data.length > 0">
            <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'downtime')" layout='table, pagination' :table-props='downtimeGrid.tableProps' :data='downtimeGrid.data' :filters='downtimeGrid.filters' :page-size='downtimeGrid.pageProps.pageSize' :pagination-props='downtimeGrid.pageProps'>
              <el-table-column :prop='downtimeGrid.titles[0].prop' :label='labels.downtimestart'    sortable='custom'>
                <template  slot-scope="props">
                {{$util.dateTimeWithTimeZone(props.row.originStartDt)}}
                </template>
              </el-table-column>
              <el-table-column :prop='downtimeGrid.titles[1].prop' :label='labels.downtimeend'    sortable='custom'>
                <template  slot-scope="props">
                {{$util.dateTimeWithTimeZone(props.row.originEndDt)}}
                </template>
              </el-table-column>
              <el-table-column :prop='downtimeGrid.titles[2].prop' :label='"DOWN TIME"'    sortable='custom'></el-table-column>
            </data-tables>
          </el-tab-pane>
          <!-- DISCI -->
          <el-tab-pane  name="disci" :label='labels.disci' v-if="disciGrid.data.length > 0">
            <div class='col'>
              <p class="grid-line"></p>
              <data-tables v-if="(activeTab !== 'list') && (activeTabDetail === 'disci')" layout='table, pagination' :table-props='disciGrid.tableProps' :data='disciGrid.data' :filters='disciGrid.filters' :page-size='disciGrid.pageProps.pageSize' :pagination-props='disciGrid.pageProps'>
                <el-table-column :prop='disciGrid.titles[0].prop' :label='labels.datasrc'          sortable='custom' :width='disciGrid.titles[0].width'></el-table-column>
                <el-table-column :prop='disciGrid.titles[1].prop' :label='labels.disciid'          sortable='custom' :width='disciGrid.titles[1].width'></el-table-column>
                <el-table-column :prop='disciGrid.titles[2].prop' :label='labels.name'             sortable='custom' :width='disciGrid.titles[2].width'></el-table-column>
                <el-table-column :prop='disciGrid.titles[3].prop' :label='labels.classstructureId' sortable='custom' :width='disciGrid.titles[3].width'></el-table-column>
                <el-table-column :prop='disciGrid.titles[4].prop' :label='labels.description'      sortable='custom' :width='disciGrid.titles[4].width'></el-table-column>
                <el-table-column :prop='disciGrid.titles[5].prop' :label='labels.discoveryDt'      sortable='custom' :width='disciGrid.titles[5].width'>
                <template  slot-scope="props">
                {{$util.dateTimeWithTimeZone(props.row.discoveryDt)}}
                </template>
              </el-table-column>
              </data-tables>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </b-row>
  </div>
</template>
<script>
import CIDetailDefault from './CIDetailDefault'
export default {
  props: ['activeTab', 'labels', 'authorities', 'comboData', 'detailType', 'listGrid', 'relationTabGrid', 'relationSwGrid', 'itsmData', 'itsmSpecGrid', 'downtimeGrid', 'disciGrid', 'activeTabFlag'],
  components: {
    'CIDetailDefault': CIDetailDefault
  },
  data () {
    return {
      specSrchGrid: {
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
        filters: [{
          prop: ['classspecId', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'chk'
        }, {
          prop: 'classspecId',
          width: '150px'
        }, {
          prop: 'description',
          width: '150px'
        }, {
          prop: 'measureunit',
          width: '110px'
        }, {
          prop: 'type',
          width: '150px'
        }],
        currentData: {},
        total: 0,
        selectedItem: [],
        searchParam: ''
      },
      pageMapList: [
        {key: '/HARDWARE/SERVER/', objNm: 'CIDetailSpecComputerSystem'},
        {key: '/HARDWARE/NETWORK/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/HARDWARE/STORAGE/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/HARDWARE/SECURE_HW/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/HARDWARE/BACKUP_HW/', objNm: 'CIDetailSpecNetStorSec'},
        {key: '/GROUP_1/', objNm: 'CIDetailSpecApplication'},
        {key: '/SOFTWARE/', objNm: 'CIDetailSpecSoftware'}
      ],
      tabActiveFlag: false,
      currentView: null,
      activeTabDetail: 'spec',
      showSpecSrchDialogFlag: false,
      displayAllFlag: false
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.activeTabDetail = 'cpu'
    },
    // USER FN ////////////////////////////////////////////
    fltrRelation: function (data, param) {
      let rtnData = data.filter((data) => {
        return data[param.fltrCol] === param.fltrStr
      })
      return rtnData
    },
    showPartTab: function () {
      if (this.$util.isNull(this.listGrid.currentData.hierarchypath)) {
        return
      }
      return this.listGrid.currentData.hierarchypath.indexOf('/SERVER/') > -1
    },
    // TRNS FN ////////////////////////////////////////////
    // EVENT FN ///////////////////////////////////////////
    changeValue: function (row) {
      if (!this.$util.isNull(row.id)) {
        row.state = 'CHANGED'
      }
    },
    enterSearchRelationSw: function () {
      this.relationSwGrid.filters[0].value = this.relationSwGrid.searchParam
    },
    enterSearchItsmSpec: function () {
      this.itsmSpecGrid.filters[0].value = this.itsmSpecGrid.searchParam
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
