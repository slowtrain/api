<!--
 Class Name  : Topology.vue
 Description : 토폴로지 공통 팝업
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.01  백승우  최초 생성
-->

<template>
  <el-dialog :visible="true" :show-close="false"  width="66%" :fullscreen="fullscreenLoading">
    <b-row>
      <b-col sm="1" >
        <b-row class="swimlane">
          <span>{{labels.business}}</span>
        </b-row>
        <b-row class="swimlane">
          <span>{{labels.application}}</span>
        </b-row>
        <b-row class="swimlane">
          <span>{{labels.software}}</span>
        </b-row>
        <b-row class="swimlane">
          <span>{{labels.hardware}}</span>
        </b-row>
      </b-col>
      <b-col sm="8" >
          <ejs-diagram v-if="topologyVisible" ref="topology" id="diagram"
          :width="width" :height="height"
          :contextMenuSettings="contextMenuSettings"
          :doubleClick="doubleClick"
          :click="click"
          :nodes="nodes"
          :connectors="links"
          :snapSettings = "snapSettings"
          ></ejs-diagram>
      </b-col>
      <b-col sm="3" style="padding-right: 2em;">
        <b-row>
          <b-col>
            <div class="form-group col-md-12">
              <label for="ciId">{{labels.ciId}}</label>
              <div class="search-component sm">
                <input id="ciId" style="width: 100%;" v-model="clickNode.ciId" class="formdata sm" v-bind:readOnly="true"/>
                <el-popover placement="right" width="120" trigger="click">
                  <div v-if="moveCIVisible" >
                    <el-button size="mini" type="text" v-on:click="moveCI" >{{labels.moveCi}}</el-button>
                    <br>
                  </div>
                  <div v-if="moveBusinessVisible">
                    <el-button size="mini" type="text" v-on:click="moveBusiness" >{{labels.moveBusiness}}</el-button>
                    <br>
                  </div>
                  <div v-if="moveDashboardVisible">
                    <el-button size="mini" type="text" v-on:click="moveDashboard" >{{labels.moveDashboard}}</el-button>
                    <br>
                  </div>
                  <div v-if="eventVisible">
                    <el-button size="mini" type="text" v-on:click="eventDetailOpen" >{{labels.eventView}}</el-button>
                    <br>
                  </div>
                  <!-- <a slot="reference" class="action-button cursor-pointer" >search</a> -->
                </el-popover>
              </div>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <div class="form-group col-md-12">
              <label for="ciname">{{labels.ciname}}</label>
              <div class="search-component sm">
                <input id="ciname" style="width: 100%;" v-model="clickNode.ciname" class="formdata sm" v-bind:readOnly="true"/>
              </div>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <div class="form-group col-md-12">
              <label for="classstructureId">{{labels.classstructureId}}</label>
              <div class="search-component sm">
                <input id="classstructureId" style="width: 100%;" v-model="clickNode.classstructureNm" class="formdata sm" v-bind:readOnly="true"/>
              </div>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <div class="form-group col-md-12">
              <label for="classstructureNm">{{labels.standardDate}}</label>
              <div class="search-component sm">
                <el-date-picker type="date" default-time="00:00:00" v-model="topologyOptions.standardDate" :clearable="false" class="formdata sm"></el-date-picker>
              </div>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <div class="form-group col-md-12">
              <el-button v-on:click="load"  style="margin-top:1em;width:100%">
                Reload
              </el-button>
            </div>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <b-row>
      <div class="col mt-2">
        <div class='d-flex justify-content-end'>
          <b-button variant="primary" class="px-3 sm" v-on:click="popupClose">{{labels.close}}</b-button>
        </div>
      </div>
    </b-row>
  </el-dialog>
</template>
<script>
import Vue from 'vue'
import {eventBus} from '@/eventBus/eventBus'
import {
  DiagramPlugin,
  SymbolPalettePlugin,
  Diagram,
  DiagramContextMenu,
  // DiagramConstraints,
  // DiagramBeforeMenuOpenEventArgs,
  // MenuEventArgs,
  SnapConstraints
  // NodeConstraints,
  // ConnectorConstraints
} from '@syncfusion/ej2-vue-diagrams'
// import data from './TopologyData'
Diagram.Inject(DiagramContextMenu)
Vue.use(DiagramPlugin)
Vue.use(SymbolPalettePlugin)

const eventCycle = 60

export default {
  name: 'BUSINESS_POPUP',
  props: {
    ciData: {
      type: Object
    }
  },
  mounted () {
    this.initialize()
    this.net()
  },
  beforeDestroy () {
    this.nodes = []
    this.links = []
    this.standardNode = {}
    this.clickNode = {}
    this.labels = {}
    this.$nextTick(function () {
      this.topologyVisible = false
      clearInterval(this.interval)
    })
  },
  data () {
    return {
      topologyVisible: false,
      fullscreenLoading: false,
      width: '100%',
      height: '410px',
      nodes: [],
      links: [],
      topologyOptions: {
        standardDate: new Date(),
        nodeSize: 40,
        width: 40,
        height: 40,
        offsetX: 100,
        offsetY: 50
      },
      contextMenuSettings: {
        show: true,
        items: [
          {
            text: 'Save',
            id: 'save'
          }
        ],
        showCustomMenuOnly: false,
        contextMenuOpen: function (DiagramBeforeMenuOpenEventArgs) {
          for (let item of DiagramBeforeMenuOpenEventArgs.items) {
            DiagramBeforeMenuOpenEventArgs.hiddenItems.push(item.text)
          }
        }
      },
      snapSettings: {
        constraints: SnapConstraints.ShowHorizontalLines,
        horizontalGridlines: { lineIntervals: [1.25, 18.75, 0, 19.75, 0, 19.75, 0, 19.75, 0, 19.75], snapIntervals: [10] }
        // horizontalGridlines: { lineIntervals: [1.25, 18.75, 0.25, 19.75, 0.25, 19.75, 0.25, 19.75, 0.25, 19.75], snapIntervals: [10] },
      },
      standardNode: {
      },
      clickNode: {
      },
      labels: {
        ciId: '',
        classstructureId: '',
        depth: '',
        close: '',
        screenshot: '',
        severity: '',
        ciName: '',
        standardDate: '',
        moveCi: '',
        moveDashboard: '',
        eventView: '',
        moveBusiness: '',
        movementsMsg: ''
      }
    }
  },
  computed: {
    moveCIVisible: function () {
      return this.clickNode.ancestor !== 'BUSINESSSYSTEM' && this.clickNode.ancestor !== 'GROUP_1'
    },
    moveBusinessVisible: function () {
      return this.clickNode.ancestor === 'BUSINESSSYSTEM' || this.clickNode.ancestor === 'GROUP_1'
    },
    moveDashboardVisible: function () {
      return this.clickNode.ancestor !== 'BUSINESSSYSTEM' && this.clickNode.ancestor !== 'GROUP_1'
    },
    eventVisible: function () {
      return this.clickNode.ancestor !== 'BUSINESSSYSTEM' && this.clickNode.ancestor !== 'GROUP_1'
    }
  },
  watch: {
  },
  methods: {
    yyyyMMdd: function (date) {
      let yyyy = date.getFullYear().toString()
      let mm = (date.getMonth() + 1).toString()
      let dd = date.getDate().toString()
      return yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + (dd[1] ? dd : '0' + dd[0])
    },
    load: function () {
      if (!(this.clickNode.ancestor === 'BUSINESSSYSTEM' || this.clickNode.ancestor === 'GROUP_1')) {
        this.$util.esmessage(this, 'warn', this.labels.movementsMsg)
        return
      }
      this.standardNode = this.clickNode
      this.nodes = []
      this.links = []
      this.net()
    },
    net: function () {
      this.$http.get(
        '/episode/api/topologybusiness/network',
        {params: {ciId: this.standardNode.ciId, classstructureId: this.standardNode.classstructureId, standardDate: this.yyyyMMdd(this.topologyOptions.standardDate)}}
      ).then(res => {
        this.standardNode = res.data.standardNode
        this.nodes = []
        this.nodes = res.data.nodes
        this.links = []
        this.links = res.data.links
      }).catch(error => {
        console.log(error)
      })
    },
    getEvent: function () {
      this.$http.get(
        '/episode/api/topologybusiness/network',
        {params: {ciId: this.standardNode.ciId, classstructureId: this.standardNode.classstructureId, standardDate: this.yyyyMMdd(this.topologyOptions.standardDate)}}
      ).then(res => {
        let newNodes = res.data.nodes
        let instancesNodes = this.$refs.topology.ej2Instances.nodes
        instancesNodes.forEach(node => {
          let newNode = newNodes.find(newNode => { return newNode.ciId === node.ciId })
          if (newNode !== undefined) {
            newNode.offsetX = node.offsetX
            newNode.offsetY = node.offsetY
          }
        })
        this.nodes = newNodes
        this.links = res.data.links
      }).catch(error => {
        console.log(error)
      })
    },
    startInterval: function () {
      this.interval = setInterval(this.getEvent, (eventCycle * 1000))
    },
    moveCI: function () {
      this.$util.move(this, 'CI', this.clickNode.uid)
    },
    moveBusiness: function () {
      this.$util.move(this, 'SERVICE', this.clickNode.uid)
    },
    moveDashboard: function () {
      this.$util.move(this, 'CIMONITORDETAIL', this.clickNode.uid)
    },
    eventDetailOpen: function () {
      eventBus.$emit('topologyEventOpen', this.clickNode.uid, this.$options.name)
    },
    initialize: function () {
      this.topologyVisible = true
      this.standardNode = this.ciData
      this.clickNode = this.standardNode
      this.$util.setLabels(this.$options.name, this.labels)
      this.startInterval()
    },
    popupClose: function () {
      eventBus.$emit('dialogClose')
    },
    dragEnter: (args) => {
      getEventDetails(args)
    },
    dragLeave: (args) => {
      getEventDetails(args)
    },
    dragOver: (args) => {
      if (args.target) {
        getEventDetails(args)
      }
    },
    click: function (event) {
      if (event.element.hasOwnProperty('ciId')) {
        this.clickNode = event.element
      }
      // getEventDetails(event)
    },
    historyChange: (args) => {
      getEventDetails(args)
    },
    doubleClick: (args) => {
      getEventDetails(args)
    },
    textEdit: (args) => {
      getEventDetails(args)
    },
    scrollChange: (args) => {
      getEventDetails(args)
    },
    selectionChange: (args) => {
      getEventDetails(args)
    },
    sizeChange: (args) => {
      getEventDetails(args)
    },
    connectionChange: (args) => {
      getEventDetails(args)
    },
    sourcePointChange: (args) => {
      getEventDetails(args)
    },
    targetPointChange: (args) => {
      getEventDetails(args)
    },
    propertyChange: (args) => {
      getEventDetails(args)
    },
    positionChange: (args) => {
      getEventDetails(args)
    },
    rotateChange: (args) => {
      getEventDetails(args)
    },
    collectionChange: (args) => {
      getEventDetails(args)
    },
    mouseEnter: (args) => {
      getEventDetails(args)
    },
    mouseLeave: (args) => {
      getEventDetails(args)
    },
    mouseOver: (args) => {
      getEventDetails(args)
    },
    contextMenuOpen: (args) => {
      getEventDetails(args)
    },
    contextMenuBeforeItemRender: (args) => {
      let listView = document.getElementById('listview-def')
      let listViewComponent = listView.ej2_instances ? listView.ej2_instances[0] : null
      if (listViewComponent) {
        getEventDetails(args)
      }
    },
    contextMenuClick: (args) => {
      getEventDetails(args)
    }
  },
  provide: {
    diagram: [DiagramContextMenu]
  }
}
/*
function clearEventLog () {
  let data = document.getElementById('EventLog')
  data.innerHTML = ''
}
*/
function getEventDetails (args) {
  if (document.getElementById('listview-def') && (document.getElementById('listview-def')).ej2_instances !== null) {
    let listView = document.getElementById('listview-def')
    let listViewComponent = listView.ej2_instances[0]
    let selectedItems = listViewComponent.getSelectedItems()
    if (selectedItems.data.length > 0) {
      let elementName = getName(selectedItems, args)
      if (elementName) {
        eventInformation(args)
      }
    } else {
      eventInformation(args)
    }
  }
}

function getName (selectedItems, args) {
  for (let i = 0; i < selectedItems.data.length; i++) {
    let eventName = selectedItems.data[i].id
    if (eventName === args.name) {
      return true
    }
  }
  return false
}

// tslint:disable-next-line:max-func-body-length
function eventInformation (args) {
  let span = document.createElement('span')
  span.innerHTML = 'Diagram ' + args.name.bold() + ' event called' + '<hr>'
  // let log = document.getElementById('EventLog');
  // log.insertBefore(span, log.firstChild);
}
</script>

<style>
    @import "../../../node_modules/@syncfusion/ej2-vue-diagrams/styles/material.css";

    .swimlane{
      height: 25%;
      margin: auto;
      padding: 50% 0;
      font-size:12px;
      font-weight: 550;
    }

    .swimlane span{
      margin-left: auto;
      margin-right: auto;
    }

</style>
