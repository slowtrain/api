<template>
  <div>
    <DisCIDetailDefault
      ref="disCIDetailDefault"
      v-bind:labels="labels"
      v-bind:authorities="authorities"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class="col-md-12">
        <b-row>
          <div class="col" style="padding-top:25px; padding-left:20px;">
            <a class="title-box">{{labels.cirelation}}</a>
          </div>
          <!-- <div class="col">
            <div class="float-right mt-3" role="group">
              <b-button variant="primary" class="px-3 sm" v-on:click="openCiRelationDialog" v-if="this.detailType=='UPDATE'">{{labels.add}}</b-button>
            </div>
          </div> -->
        </b-row>
        <div class='row mt-2'>
          <div class='col'>
            <p class="grid-line"></p>
            <data-tables layout='table, pagination' :data='relationGrid.data' :filters='relationGrid.filters' :page-size='relationGrid.pageProps.pageSize' :pagination-props='relationGrid.pageProps'>
              <el-table-column :prop='relationGrid.titles[0].prop' :label='labels.relationname'     sortable='custom' :width='relationGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[1].prop' :label='labels.sourceci'         sortable='custom' :width='relationGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[2].prop' :label='labels.name'             sortable='custom' :width='relationGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[3].prop' :label='labels.classstructureId' sortable='custom' :width='relationGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[4].prop' :label='labels.targetci'         sortable='custom' :width='relationGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[5].prop' :label='labels.name'             sortable='custom' :width='relationGrid.titles[5].width'></el-table-column>
              <el-table-column :prop='relationGrid.titles[6].prop' :label='labels.classstructureId' sortable='custom' :width='relationGrid.titles[6].width'></el-table-column>
              <el-table-column align="right" width="80">
                <template  slot-scope="props">
                  <el-button v-on:click="moveCi(props.row)" :title="labels.move" style="padding: 0; width:30px; height: 30px;">
                    <img src="../../../../assets/img/icons/icon_search_login.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </div>
      </div>
    </b-row>
  </div>
</template>
<script>
import DisCIDetailDefault from './DisCIDetailDefault'
export default {
  props: ['labels', 'authorities', 'listGrid', 'relationGrid'],
  components: {
    'DisCIDetailDefault': DisCIDetailDefault
  },
  data () {
    return {}
  },
  methods: {
    moveCi: function (row) {
      let param = {id: ''}
      if (this.listGrid.currentData.id !== row.srcdisId) {
        param.id = row.srcdisId
      } else {
        param.id = row.tgtdisId
      }
      this.$emit('moveCi', param)
    }
  },
  created: function () {}
}
</script>
