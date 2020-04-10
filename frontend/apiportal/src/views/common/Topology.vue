<!--
 Class Name  : Topology.vue
 Description : 토폴로지 공통 팝업
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.01  백승우  최초 생성
-->
<template>
  <div>
    <el-dialog :visible="true" :show-close="false" width="70%" :fullscreen="topologyOptions.isFullScreen"  >
      <!--
      <div class='row'>
        {{ciData.ciId}}
      </div>
      -->
      <b-row>
        <b-col sm="9">
          <b-row>
            <div class="form-group col-md-2">
              <label for="ciId">{{labels.ciId}}</label>
              <div class="search-component sm">
                <input id="ciId" v-model="standardNode.ciId" class="formdata sm" v-bind:readOnly="true"/>
                <el-popover placement="right" width="120" trigger="click">
                  <el-button size="mini" type="text" v-on:click="openCIDetail" >{{labels.ciDetail}}</el-button>
                  <br>
                  <el-button size="mini" type="text" v-on:click="moveCI" >{{labels.moveCi}}</el-button>
                  <br>
                  <el-button size="mini" type="text" v-on:click="moveDashboard" >{{labels.moveDashboard}}</el-button>
                  <br>
                  <el-button size="mini" type="text" v-on:click="eventDetailOpen" >{{labels.eventView}}</el-button>
                  <a slot="reference" class="action-button cursor-pointer" >search</a>
                </el-popover>
              </div>
            </div>
            <div class="form-group col-md-4">
              <label for="ciName">{{labels.ciName}}</label>
              <input id="ciName" class="formdata sm"  v-model="standardNode.name" v-bind:readOnly="true"/>
            </div>
            <div class="form-group col-md-1">
              <el-checkbox style="margin-top:2em" v-model="standardNode.bfi" disabled >BFI</el-checkbox>
            </div>
            <div class="form-group col-md-1">
              <el-checkbox style="margin-top:2em" v-model="standardNode.taddm" disabled >TADDM</el-checkbox>
            </div>
            <div class="form-group col-md-1">
              <el-checkbox style="margin-top:2em" v-model="standardNode.watchall" disabled >EMS</el-checkbox>
            </div>
            <div class="form-group col-md-2">
              <label>{{labels.standardDate}}</label>
              <el-date-picker type="date" default-time="00:00:00" v-model="topologyOptions.standardDate" :clearable="false" class="formdata sm"></el-date-picker>
            </div>
            <div class="form-group col-md-1" style="padding-left:12px;">
                <label for="Depth">{{labels.depth}}</label>
                <el-select id="Depth" v-model="topologyOptions.depthLimit" >
                  <el-option  :value="1" :key="1">1</el-option>
                  <el-option  :value="2" :key="2">2</el-option>
                  <el-option  :value="3" :key="3">3</el-option>
                </el-select>
            </div>
          </b-row>
          <b-row style="margin-top:1em">
            <div class="form-group col-md-2">
              <label>RUN</label>
              <el-switch v-model="relationFlag.run" ></el-switch>
            </div>
            <div class="form-group col-md-2">
              <label>DEPEND</label>
              <el-switch v-model="relationFlag.depend" ></el-switch>
            </div>
            <div class="form-group col-md-2">
              <label>VIRTUALIZE</label>
              <el-switch v-model="relationFlag.virtualize" ></el-switch>
            </div>
            <div class="form-group col-md-2">
              <label>INCLUDE</label>
              <el-switch v-model="relationFlag.include" ></el-switch>
            </div>
            <div class="form-group col-md-2">
              <label>CLUSTER</label>
              <el-switch v-model="relationFlag.cluster"></el-switch>
            </div>
            <div class="form-group col-md-2">
              <label>CONNECT</label>
              <el-switch v-model="relationFlag.connect"></el-switch>
            </div>
            <!--
            <div class="form-group col-md-2">
              <label>ICON</label>
              <el-switch v-model="topologyOptions.useIcon"></el-switch>
            </div>
            -->
          </b-row>
        </b-col>
        <b-col sm="1" >
          <b-row >
            <el-button v-on:click="load"  style="margin:auto;margin-top:1em">
              Reload
            </el-button>
          </b-row>
        </b-col>
        <b-col sm="2" >
          <b-row>
            <div class="form-group">
              <el-switch v-model="topologyOptions.nodeLabels" ></el-switch>
              <label>SHOW NAME</label>
            </div>
          </b-row>
          <b-row>
            <div class="form-group">
              <el-switch v-model="topologyOptions.linkLabels" ></el-switch>
              <label>SHOW RELATION</label>
            </div>
          </b-row>
          <b-row>
            <div class="form-group">
              <el-switch v-model="topologyOptions.isFullScreen" ></el-switch>
              <label>FULL SCREEN</label>
            </div>
          </b-row>
        </b-col>
      </b-row>
      <p style="border: thin solid #dbdbe0; margin-top:1em;"></p>
      <b-row>
        <b-col sm="1" >
          <div style="position:relative;left:2em;top:4em;">
            <el-slider v-model="nodeSliderOptions.size" :step="nodeSliderOptions.step" :max="nodeSliderOptions.max" :height="nodeSliderOptions.height" vertical  show-stops>
            </el-slider>
          </div>
        </b-col>
        <b-col sm="11" >
          <b-row>
            <b-col ref="topologyContainer" v-on:click="topologyInit" >
              <div  >
                <d3network v-if="topologyVisible" ref="topology" @node-click="nodeClick" :net-nodes="nodes" :net-links="links" :options="topologyOptions" :style="topologyStyle">
                </d3network>
              </div>
            </b-col>
          </b-row>
          <!--
          <b-row style="margin-top:2em">
            <b-col>
              <el-slider v-model="offsetXSliderOptions.x" :step="offsetXSliderOptions.step" :max="offsetXSliderOptions.max"  >
              </el-slider>
            </b-col>
          </b-row>
          -->
        </b-col>
        <!--
        <b-col sm="1">
          <div style="position:relative;left:1em;top:4em;">
            <el-slider v-model="offsetYSliderOptions.y" :step="offsetYSliderOptions.step" :max="offsetYSliderOptions.max" :height="offsetYSliderOptions.height" vertical >
            </el-slider>
          </div>
        </b-col>
        -->
      </b-row>
      <b-row>
        <b-col>
        </b-col>
      </b-row>
      <span slot="footer" class="dialog-footer">
        <p style="border: thin solid #dbdbe0; "></p>
        <span style="margin-right:1em">Node Count : {{this.nodes.length}} </span>
        <span style="margin-right:1em">Link Count : {{this.links.length}} </span>
        <!-- <b-button variant="primary" v-on:click="screenShot"  class="px-3">{{labels.screenshot}}</b-button> -->
        <b-button variant="primary" v-on:click="popupClose"  class="px-3">{{labels.close}}</b-button>
      </span>
    </el-dialog>
    <CIDetail
      ref="cIDetailDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showCIDialogFlag"
      v-bind:param="{id:this.standardNode.uid}"
      v-on:close="closeCIDialog"
    />
  </div>
</template>

<script>
import d3network from 'vue-d3-network'
import {eventBus} from '@/eventBus/eventBus'
import CIDetail from '@/views/common/CIDetail'
const eventCycle = 60

const resizeRatio = {
  large: {
    nodeSlider: {
      h: 0.46
    },
    topology: {
      w: 0.88,
      h: 0.58
    }
  },
  normal: {
    nodeSlider: {
      h: 0.3
    },
    topology: {
      w: 0.6,
      h: 0.4
    }
  }
}

export default {
  name: 'TOPOLOGY_POPUP',
  components: {
    d3network,
    CIDetail
  },
  props: {
    ciData: {
      type: Object
    }
  },
  mounted () {
    this.initialize()
    this.renderNode(this.ciData.ciId)
  },
  beforeDestroy () {
    this.nodes = []
    this.links = []
    this.standardNode = {}
    this.searchedNodes = []
    this.labels = {}
    this.$nextTick(function () {
      this.topologyVisible = false
      clearInterval(this.interval)
    })
  },
  data () {
    return {
      topologyVisible: false,
      relationFlag: {
        include: true,
        virtualize: true,
        run: true,
        depend: true,
        cluster: true,
        connect: false
      },
      interval: null,
      nodeSliderOptions: {
        step: 1,
        max: 20,
        min: 5,
        size: 11,
        height: (Math.floor(screen.height * resizeRatio.normal.nodeSlider.h / 10) * 10) + 'px'
      },
      standardNode: {
      },
      searchedNodes: [],
      nodes: [
        // {id: 1, name: '192.168.0.1', svgSym: iconMap['COMPUTERSYSTEM'], _color: '#63c2de'}
      ],
      links: [
        // { sid: 1, tid: 2, _color: '#73818f' }
      ],
      topologyOptions: {
        standardDate: new Date(),
        isFullScreen: false,
        depthLimit: 1,
        size: {
          w: 3000,
          h: 3000
        },
        containerSize: {
          w: (Math.floor(screen.width * resizeRatio.normal.topology.w / 10) * 10),
          h: (Math.floor(screen.height * resizeRatio.normal.topology.h / 10) * 10)
        },
        offset: {
          x: 0,
          y: 0
        },
        useIcon: true,
        canvas: false,
        force: 1000,
        nodeSize: 25,
        nodeLabels: true,
        linkLabels: false,
        linkWidth: 1,
        resizeListener: false,
        clickColor: '#409EFF',
        noneClickColor: '#c8ced3'
      },
      labels: {
        standardDate: '기준일'
      },
      showCIDialogFlag: false
    }
  },
  computed: {
    topologyStyle: function () {
      let style = 'overflow: scroll;margin: 0 auto;'
      style += 'width: ' + this.topologyOptions.containerSize.w + 'px;'
      style += 'height: ' + this.topologyOptions.containerSize.h + 'px;'
      return style
    }
  },
  watch: {
    'topologyOptions.isFullScreen': function (val) {
      if (val) {
        this.topologyOptions.containerSize.w = (Math.floor(screen.width * resizeRatio.large.topology.w / 10) * 10)
        this.topologyOptions.containerSize.h = (Math.floor(screen.height * resizeRatio.large.topology.h / 10) * 10)
        this.nodeSliderOptions.height = (Math.floor(screen.height * resizeRatio.large.nodeSlider.h / 10) * 10) + 'px'
      } else {
        this.topologyOptions.containerSize.w = (Math.floor(screen.width * resizeRatio.normal.topology.w / 10) * 10)
        this.topologyOptions.containerSize.h = (Math.floor(screen.height * resizeRatio.normal.topology.h / 10) * 10)
        this.nodeSliderOptions.height = (Math.floor(screen.height * resizeRatio.normal.nodeSlider.h / 10) * 10) + 'px'
      }
      this.$refs.topology.reset()
    },
    'topologyOptions.standardDate': function (val) {
      if (val == null) {
        this.topologyOptions.standardDate = new Date()
      }
    },
    'topologyOptions.nodeLabels': function (val) {
      this.$refs.topology.updateOptions(this.topologyOptions)
    },
    'topologyOptions.linkLabels': function (val) {
      this.$refs.topology.updateOptions(this.topologyOptions)
    },
    'nodeSliderOptions.size': function (val) {
      val *= 1
      this.nodes.map((node) => {
        node._size = node.original_size * ((val === 0 ? 1 : val) / 10)
      })
    }
  },
  methods: {
    yyyyMMdd: function (date) {
      let yyyy = date.getFullYear().toString()
      let mm = (date.getMonth() + 1).toString()
      let dd = date.getDate().toString()
      return yyyy + '-' + (mm[1] ? mm : '0' + mm[0]) + '-' + (dd[1] ? dd : '0' + dd[0])
    },
    initialize: function () {
      this.topologyVisible = true
      this.$util.setLabels(this.$options.name, this.labels)
      this.startInterval()
    },
    topologyInit: function (event) {
      if (event.srcElement.childNodes.length > 0) {
        this.nodes.map((node) => {
          if (node.event.length < 1) {
            node._color = node.original_color
            node._size = node.original_size * ((this.nodeSliderOptions.size === 0 ? 1 : this.nodeSliderOptions.size) / 10)
          }
          node.connected = false
        })
        this.links.map((link) => {
          link._color = link.original_color
        })
      }
    },
    moveCI: function () {
      this.$util.move(this, 'CI', this.standardNode.uid)
    },
    openCIDetail: function () {
      this.showCIDialogFlag = true
    },
    closeCIDialog: function () {
      this.showCIDialogFlag = false
    },
    moveDashboard: function () {
      this.$util.move(this, 'CIMONITORDETAIL', this.standardNode.uid)
    },
    screenShot: function () {
      this.$refs.topology.screenShot(this.standardNode.ciId, 'white', true, false)
      /*
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      setTimeout(() => {
        this.$refs.topology.screenShot(this.standardNode.ciId, 'white', true, true)
        loading.close()
      }, 1000)
      */
    },

    startInterval: function () {
      this.interval = setInterval(this.getEventList, (eventCycle * 1000))
    },
    getEventList: function () {
      this.$http.get(
        '/episode/api/topology/events'
      ).then(res => {
        let events = res.data.events
        this.nodes.forEach((node) => {
          let nodeEvents = events.filter((event) => { return node.ciId === event.ci_id })
          if (nodeEvents.length > 0) {
            nodeEvents.sort(function (a, b) {
              return a.level < b.level ? -1 : a.level > b.level ? 1 : 0
            })
            node._color = nodeEvents[0]._color
            node.event = nodeEvents
          } else {
            if (node.connected) {
              node._color = this.topologyOptions.clickColor
            }
            node.event = []
          }
        })
      }).catch(error => {
        console.log(error)
      })
    },
    nodeClick: function (event, node) {
      this.nodes.map((nod) => {
        nod.connected = false
        nod._size = nod.original_size * ((this.nodeSliderOptions.size === 0 ? 1 : this.nodeSliderOptions.size) / 10)
        if (nod.event.length < 1) {
          nod._color = this.topologyOptions.noneClickColor
        }
      })
      this.links.map((link) => {
        link._color = this.topologyOptions.noneClickColor
      })
      this.links.filter((link) => {
        return link.sid === node.id || link.tid === node.id
      }).forEach((link) => {
        this.nodes.filter((nod) => {
          return link.sid === nod.id || link.tid === nod.id
        }).map((nod) => {
          nod.connected = true
          if (nod.event.length < 1) {
            nod._color = this.topologyOptions.clickColor
          }
        })
        link._color = this.topologyOptions.clickColor
      })
      node._size = node.original_size * (((this.nodeSliderOptions.size === 0 ? 1 : this.nodeSliderOptions.size) / 10) * 1.3)
      this.standardNode = node
    },
    load: function () {
      this.nodes = []
      this.links = []
      this.searchedNodes = []
      this.renderNode(this.standardNode.ciId, 0)
    },
    renderNode: function (ciId) {
      this.$http.get(
        '/episode/api/topology/node', {params: {ciId: ciId, depth: 0, nodeSize: this.topologyOptions.nodeSize, useIcon: this.topologyOptions.useIcon, standardDate: this.yyyyMMdd(this.topologyOptions.standardDate)}}
      ).then(res => {
        this.searchedNodes.push(res.data.node.ciId)
        this.standardNode = res.data.node
        this.standardNode._size = this.standardNode.original_size * ((this.nodeSliderOptions.size === 0 ? 1 : this.nodeSliderOptions.size) / 10)
        this.standardNode.event = []
        this.standardNode.connected = false
        this.standardNode.offset = {
          x: 100,
          y: 1000
        }
        this.nodes.push(this.standardNode)
        this.renderLink(res.data.node.ciId, 1)
        this.getEventList()
      }).catch(error => {
        console.log(error)
      })
    },
    renderLink: function (ciId, depth) {
      let param = {
        ciId: ciId,
        depth: depth,
        nodeSize: this.topologyOptions.nodeSize,
        useIcon: this.topologyOptions.useIcon,
        standardDate: this.yyyyMMdd(this.topologyOptions.standardDate),
        INCLUDE: this.relationFlag.include,
        VIRTUALIZE: this.relationFlag.virtualize,
        CONNECT: this.relationFlag.connect,
        RUN: this.relationFlag.run,
        DEPEND: this.relationFlag.depend,
        CLUSTER: this.relationFlag.cluster
      }
      this.$http.get(
        '/episode/api/topology/links', {params: param}
      ).then(res => {
        res.data.nodes.filter((newNode) => {
          return (this.nodes.filter((node) => { return node.id === newNode.id }) < 1)
        }).forEach(newNode => {
          if (this.nodes.filter((node) => { return node.id === newNode.id }) < 1) {
            newNode._size = newNode.original_size * ((this.nodeSliderOptions.size === 0 ? 1 : this.nodeSliderOptions.size) / 10)
            newNode.event = []
            newNode.connected = false
            this.searchedNodes.push(newNode.ciId)
            this.nodes.push(newNode)
            if (depth < this.topologyOptions.depthLimit) {
              let newDepth = newNode.depth
              newDepth++
              this.renderLink(newNode.ciId, newDepth)
            }
          }
        })

        res.data.links.filter((newLink) => {
          return (this.links.filter((link) => { return link.id === newLink.id }) < 1)
        }).forEach(newLink => {
          if (this.links.filter((link) => { return link.id === newLink.id }) < 1) {
            this.links.push(newLink)
          }
        })
      }).catch(error => {
        console.log(error)
      })
    },
    popupClose: function () {
      this.$emit('topologyClose')
      // this.$parent.hideTopology()
    },
    eventDetailOpen: function () {
      eventBus.$emit('topologyEventOpen', this.standardNode.uid, this.$options.name)
    }
  }
}
</script>
<style >
/*
.title {
  position:absolute;
  text-align: center;
  left: 2em;
}
h1,a {
  color: #1aad8d;
  text-decoration: none;
}
*/
/*
ul.menu {
  list-style: none;
  position: absolute;
  z-index: 100;
  min-width: 20em;
  text-align: left;
}
ul.menu li {
  margin-top: 1em;
  position: relative;
}

#m-end path, #m-start {
  fill: rgba(18, 120, 98, 0.8);
}

.title {
  margin-bottom:3em;
}
*/

.title {
  background-color: blueviolet
}

.root .node{
  border: 2px solid red;
  border-color: yellow;
}

circle {
  border: 2px solid red;
  border-color: red;
}

</style>
