<!--
 Class Name  : CIHis.vue
 Description : IT 자원 > 히스토리
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
    <CIDetailDefault
      ref="ciDetailDefault"
      v-if="(activeTab !== 'list') && this.activeTabFlag.his"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class="col-md-12">
        <b-row>
          <div class="col" style="padding-top:25px; padding-left:20px;">
            <a class="title-box">{{labels.his}}</a>
          </div>
          <div class="col">
            <div class="float-right mt-3" style="height:34px;" role="group">
            </div>
          </div>
        </b-row>
        <div class='row mt-2'>
          <div class='col'>
            <p class="grid-line"></p>
            <data-tables v-if="(activeTab !== 'list') && this.activeTabFlag.his" layout='table, pagination' :table-props='hisGrid.tableProps' :data='hisGrid.data' :filters='hisGrid.filters' :page-size='hisGrid.pageProps.pageSize' :pagination-props='hisGrid.pageProps'>
              <el-table-column :prop='hisGrid.titles[0].prop' :label='labels.updateNm'        sortable='custom' :width='hisGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='hisGrid.titles[1].prop' :label='labels.updateDt'        sortable='custom' :width='hisGrid.titles[1].width'>
                <template slot-scope="props">
                  {{$util.dateTimeWithTimeZone(props.row.updateDt)}}
                </template>
              </el-table-column>
              <el-table-column :prop='hisGrid.titles[2].prop' :label='labels.chgtype'         sortable='custom' :width='hisGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='hisGrid.titles[3].prop' :label='labels.chgitem'         sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='hisGrid.titles[4].prop' :label='labels.chgdescription'  sortable='custom' ></el-table-column>
            </data-tables>
          </div>
        </div>
      </div>
    </b-row>
  </div>
</template>
<script>
import CIDetailDefault from './CIDetailDefault'
export default {
  props: ['activeTab', 'labels', 'comboData', 'detailType', 'listGrid', 'hisGrid', 'activeTabFlag'],
  components: {
    'CIDetailDefault': CIDetailDefault
  },
  data () {
    return {
      tabActiveFlag: false
    }
  },
  methods: {},
  created: function () {}
}
</script>
