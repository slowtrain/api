<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="2" >
          <div>
            <b-button class="move-btn" v-show="(activeTab !== 'list') && listGrid.currentData.hasOwnProperty('id')" v-on:click="moveChk('previous')">
              <img src="../../../assets/img/icons/arrow-left@2x.png"/>
            </b-button>
            <b-button class="move-btn" v-show="(activeTab !== 'list') && listGrid.currentData.hasOwnProperty('id')" v-on:click="moveChk('next')">
              <img src="../../../assets/img/icons/arrow-right@2x.png"/>
            </b-button>
          </div>
        </b-col>
        <b-col sm='10'>
          <div class="comm-btn-box" role="group">
            <b-button variant="primary" class="px-3 sm" v-show="visibleDelete"   v-on:click="del">{{labels.delete}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleSave"     v-on:click="save">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleAdd"  v-on:click="add">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <el-tabs v-model="activeTab" :before-leave="changeTab">
        <el-tab-pane label="목록" name="list">
            <b-row>
            <div class='col-md-4'>
              <div class="search-component sm">
                <input class="form-control formdata sm" type="text" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder="labels.search" />
              </div>
            </div>
            <div class='col-md-8' >
              <div class='d-flex justify-content-end'>
                <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                  <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                </el-button>
              </div>
            </div>
          </b-row>

          <b-row>
            <div class="col">
              <p class='grid-line'></p>
              <data-tables layout='table, pagination' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps'
                :current-page.sync='listGrid.pageIndex'
                v-on:row-click="handleRowClick"
                v-on:filtered-data="handleFilteredData">
                <el-table-column prop="wfprocessId"        :label="labels.wfprocessId"        :sortable="true" :width='listGrid.titles[0].width'></el-table-column>
                <el-table-column prop="objectId"        :label="labels.objectId"        :sortable="true" :width='listGrid.titles[1].width'></el-table-column>
                <el-table-column prop="status"        :label="labels.status"        :sortable="true" :width='listGrid.titles[2].width'></el-table-column>
                <el-table-column prop="description"    :label="labels.description"    :sortable="true" ></el-table-column>
                <el-table-column prop="status"    :label="labels.status"    :sortable="true" :width='listGrid.titles[4].width'></el-table-column>
                <el-table-column prop="createDt"  :label="labels.createDt"  :sortable="true" :width='listGrid.titles[5].width'>
                  <template slot-scope="scope">{{dateTime(scope.row.createDt)}}</template>
                </el-table-column>
              </data-tables>
            </div>
          </b-row>
        </el-tab-pane >
        <el-tab-pane label="상세" name="detail" >
          <b-row>
             <div class='col'>
              <!-- wfprocess_id, revision, description, status, enabled, object_id -->
              <el-form :model="listGrid.currentData" ref="userForm" label-width="120px" >
                <div class="form-row mt-2">
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="id" >{{labels.id}}<span class="require-mark">*</span></label>
                    <input type="text" id="id" class="form-control formdata sm" v-model="listGrid.currentData.id"  :disabled="true" />
                  </div>
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="wfprocessId">{{labels.wfprocessId}}<span class="require-mark">*</span></label>
                    <input type="text" value="test1" id="wfprocessId" class="form-control formdata sm" v-model="listGrid.currentData.wfprocessId"  v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData" :disabled="wfprocessIdDisabled" />
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.wfprocessId)" v-text="validMsg.wfprocessId"></p>
                  </div>
                  <div class="form-group col-md-6" style="padding-right:1%">
                    <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
                    <input type="text" id="description" class="form-control formdata sm" v-model="listGrid.currentData.description"  v-on:change="changeValue(listGrid.currentData)" maxlength="11"/>
                  </div>
                </div>
                <div class="form-row mt-2">
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="objectId">{{labels.objectId}}<span class="require-mark">*</span></label>
                    <el-select v-model="listGrid.currentData.objectId" style="width:100%" :placeholder="labels.selectData">
                      <el-option v-for="item in tableList" :label="item.tableName" :value="item.tableName" :key="item.tableName">
                      </el-option>
                    </el-select>
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="status">{{labels.status}}<span class="require-mark">*</span></label>
                    <el-select v-model="listGrid.currentData.status" style="width:100%" :placeholder="labels.selectData">
                      <el-option v-for="item in statusList" :label="item.key" :value="item.key" :key="item.key">
                      </el-option>
                    </el-select>
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="revision">{{labels.revision}}<span class="require-mark">*</span></label>
                    <input id="revision" class="form-control formdata sm" v-model="listGrid.currentData.revision" placeholder="REVISION" disabled="disabled"/>
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="enabled">{{labels.enabled}}</label>
                    <el-switch v-model="listGrid.currentData.enabled" class="formdata sm" disabled="disabled" ></el-switch>
                  </div>
                </div>
                <div class="form-row mt-2">
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="createId">{{labels.createId}}</label>
                    <input id="createId" class="form-control formdata sm" v-model="listGrid.currentData.createId" placeholder="CREATER" disabled="disabled" />
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="createDt">{{labels.createDt}}</label>
                    <input id="createDt" class="form-control formdata sm" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled"/>
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="updateId">{{labels.updateId}}</label>
                    <input id="updateId" class="form-control formdata sm" v-model="listGrid.currentData.updateId" placeholder="UPDATER" disabled="disabled" />
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="updateDt">{{labels.updateDt}}</label>
                    <input id="updateDt" class="form-control formdata sm" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                  </div>
                </div>
              </el-form>
            </div>
          </b-row>
          <b-row>
            <b-col>
              <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <el-card shadow="never">
                <div class="control-section">
                  <div id="palette-space" class="sb-mobile-palette">
                    <ejs-symbolpalette id="symbolpalette" :expandMode='expandMode' :palettes='palettes' :width='palettewidth' :height='paletteheight' :getNodeDefaults='palettegetNodeDefaults' :getSymbolInfo='getSymbolInfo' :symbolMargin='symbolMargin' :symbolHeight='symbolHeight'
                    :symbolWidth='symbolWidth'></ejs-symbolpalette>
                  </div>
                  <div id="diagram-space" class="sb-mobile-diagram" style="border: 1px solid #73818f">
                    <ejs-diagram v-if="visibleDiagram" ref="diagram" style='display:block' id="diagram" :width="width" :height="height" :nodes="nodes" :connectors="connectors" :getNodeDefaults='getNodeDefaults' :getConnectorDefaults='getConnectorDefaults' :dragEnter='dragEnter'
                                  :snapSettings="snapSettings" :created="created"
                                  :contextMenuSettings="contextMenuSettings"
                                  :contextMenuOpen="contextMenuOpen"
                                  :contextMenuBeforeItemRender="contextMenuBeforeItemRender"
                                  :contextMenuClick="contextMenuClick"
                                  :selectionChange="selectionChange"
                                  :sourcePointChange="sourcePointChange"
                                  :targetPointChange="targetPointChange"
                                  :dragSourceEnd="dragSourceEnd"
                                  :click="click"
                                  ></ejs-diagram>
                  </div>
                </div>
              </el-card>
            </b-col>
          </b-row>
        </el-tab-pane >
      </el-tabs>
      <el-dialog :title="labels.propertiesTitle" :visible.sync="propertiesVisible" width="30%" >
        <el-form ref="inputForm" label-width="120px">
          <b-row class="mt-2" >
            <div class="col-md-3">
              <label>Desc</label>
            </div>
            <div class="col-md-9">
              <input class="formdata sm" v-model="properties.annotations_content" />
            </div>
          </b-row>
          <b-row class="mt-2" v-if="visibleApp">
            <div class="col-md-3">
              <label>Applicaton</label>
            </div>
            <div class="col-md-9">
              <input class="formdata sm" v-model="properties.appId"/>
            </div>
          </b-row>
          <b-row class="mt-2" v-if="visibleAction">
            <div class="col-md-3" >
              <label>Action</label>
            </div>
            <div class="col-md-9">
              <el-select  v-model="properties.actionId" style="width:100%" :placeholder="labels.selectData">
                <el-option v-for="item in actionList"  :key="item.actionId" :label="item.actionId" :value="item.actionId">
                </el-option>
              </el-select>
            </div>
          </b-row>
          <b-row class="mt-2" v-if="visibleAssignment">
            <div class="col-md-3" >
              <label>Assignment</label>
            </div>
            <div class="col-md-9">
              <el-select  v-model="properties.assignmentId" style="width:100%" :placeholder="labels.selectData">
                <el-option v-for="item in assignmentList"  :key="item.assignmentId" :label="item.assignmentId" :value="item.assignmentId">
                </el-option>
              </el-select>
            </div>
          </b-row>
          <b-row class="mt-2" v-if="visibleExpression">
            <div class="col-md-3">
              <label>Expression</label>
            </div>
            <div class="col-md-9">
              <input class="formdata sm" v-model="properties.expression"/>
            </div>
          </b-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <b-button class='px-3 sm' variant="primary" v-on:click="propertiesClose">닫기</b-button>
          <b-button class='px-3 sm' variant="primary" v-on:click="propertiesSave">저장</b-button>
        </span>
      </el-dialog>
    </b-card>
  </div>
</template>

<script>
import config from './config'
import Vue from 'vue'
// import { Browser } from '@syncfusion/ej2-base'
import {
  DiagramPlugin,
  SymbolPalettePlugin,
  NodeConstraints,
  DiagramContextMenu,
  Diagram
  // DiagramBeforeMenuOpenEventArgs,
  // MenuEventArgs
  // NodeModel,
  // UndoRedo,
  // ConnectorModel,
  // PointPortModel,
  // Connector,
  // SymbolInfo,
  // IDragEnterEventArgs,
  // GridlinesModel
} from '@syncfusion/ej2-vue-diagrams'

Diagram.Inject(DiagramContextMenu)
Vue.use(DiagramPlugin)
Vue.use(SymbolPalettePlugin)
let isMobile

export default {
  name: 'WORKFLOW',
  components: {
  },
  computed: {
    wfprocessIdDisabled: function () {
      return (this.listGrid.currentData.hasOwnProperty('id'))
    },
    visibleAdd: function () {
      // return this.authorities.USER_SAVE && (this.activeTab === 'list')
      return (this.activeTab === 'list')
    },
    visibleDelete: function () {
      // return this.authorities.USER_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
      return (this.activeTab !== 'list')
    },
    visibleSave: function () {
      // return this.authorities.USER_SAVE && (this.activeTab !== 'list')
      return (this.activeTab !== 'list')
    },
    visibleDiagram: function () {
      return (this.activeTab !== 'list')
    },
    visibleContent: function () {
      return this.actualObject !== undefined
    },
    visibleApp: function () {
      return false
      // return this.actualObject !== undefined && this.actualObject.constructor.name === 'Node'
    },
    visibleAction: function () {
      return this.actualObject !== undefined && this.actualObject.type !== undefined
    },
    visibleExpression: function () {
      return this.actualObject !== undefined && this.actualObject.type === undefined && this.actualObject.shape.shape === 'Decision'
    },
    visibleAssignment: function () {
      let result = this.actualObject !== undefined
      result = result && this.actualObject.type === undefined
      result = result && (this.actualObject.shape.shape === 'Process' || this.actualObject.shape.shape === 'Data')
      return result
    }
  },
  watch: {
    // 'activeTab': function (val) {}
  },
  methods: {
    propertiesClose: function () {
      this.propertiesVisible = false
    },
    propertiesSave: function () {
      let type = this.properties.hasOwnProperty('wfnodeId') ? 'node' : 'connector'
      this.$http.put(
        '/episode/api/system/workflow/' + this.listGrid.currentData.id + '/' + type + '/' + this.properties.id, this.properties
      ).then(res => {
        if (type === 'node') {
          this.actualObject.annotations[0].content = res.data.annotations_content
        }
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
        this.propertiesVisible = false
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.$http.get(
        '/episode/api/system/common/table'
      ).then(res => {
        this.tableList = res.data
      })
      this.$http.get(
        '/episode/api/system/common/domain', {params: {domainId: 'WFPROCESSSTATUS'}}
      ).then(res => {
        this.statusList = res.data
      })
      this.$http.get(
        '/episode/api/system/workflow/action'
      ).then(res => {
        this.actionList = res.data
      })
      this.$http.get(
        '/episode/api/system/workflow/assignment'
      ).then(res => {
        this.assignmentList = res.data
      })
    },
    changeTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        this.nodes = []
        this.connectors = []
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.activeTab = 'list'
              $this.listGrid.currentData = {}
              $this.listGrid.searchParam = ''
            })
        } else {
          this.listGrid.searchParam = ''
          this.listGrid.currentData = {}
          for (let key in this.validMsg) {
            this.validMsg[key] = ''
          }
        }
      }
      return rtnFlag
    },
    isvalidated: function () {
      let rtnFlag = true
      if (this.$util.isNull(this.listGrid.currentData.wfprocessId)) {
        this.validMsg.wfprocessIdMsg = this.labels.wfprocessIdMsg
        rtnFlag = false
      }
      if (this.$util.isNull(this.listGrid.currentData.description)) {
        this.validMsg.descriptionMsg = this.labels.desMessage
        rtnFlag = false
      }
      return rtnFlag
    },
    add: function () {
      this.listGrid.currentData = {
        objectId: 'CI',
        status: 'ACTIVE',
        revision: 0,
        enabled: true
      }
      this.activeTab = 'detail'
      this.nodes = INIT_NODES
    },
    save: function () {
      let nodes = []
      for (let node of this.$refs.diagram.ej2Instances.nodes) {
        console.log('save', node)
        let violated = false
        let shape = node.shape.shape
        if (shape === 'Terminator') {
          if (node.id === 'Start') {
            violated = this.$refs.diagram.ej2Instances.connectors.filter(item => {
              return item.sourceID === node.id
            }).length !== 1
          } else if (node.id === 'End') {
            violated = this.$refs.diagram.ej2Instances.connectors.filter(item => {
              return item.targetID === node.id
            }).length < 1
          }
        } else if (shape === 'Process') {
          violated = this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.targetID === node.id
          }).length < 1
          violated = violated || this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.sourceID === node.id
          }).length !== 1
        } else if (shape === 'Decision') {
          violated = this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.targetID === node.id
          }).length < 1
          violated = violated || this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.sourceID === node.id
          }).length !== 2
        } else if (shape === 'Data') {
          violated = this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.targetID === node.id
          }).length < 1
          violated = violated || this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.sourceID === node.id
          }).length < 1
        } else if (shape === 'Delay') {
          violated = this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.targetID === node.id
          }).length < 1
          violated = violated || this.$refs.diagram.ej2Instances.connectors.filter(item => {
            return item.sourceID === node.id
          }).length !== 1
        }
        if (violated) {
          this.$util.esmessage(this, 'warn', '정상적이지 못한 노드가 존재합니다.')
          return
        }
        nodes.push({
          wfnodeId: node.id,
          offsetX: node.offsetX,
          offsetY: node.offsetY,
          constraints: node.constraints,
          annotations_content: node.annotations[0].content,
          style_fill: node.style.fill,
          shape_type: node.shape.type,
          shape_shape: node.shape.shape
        })
      }
      let connectors = []
      for (let connector of this.$refs.diagram.ej2Instances.connectors) {
        if (connector.sourceID === '' || connector.targetID === '') {
          this.$util.esmessage(this, 'warn', '정상적이지 못한 연결선이 존재합니다.')
          return
        }
        connectors.push({
          wfconnectorId: connector.id,
          sourceID: connector.sourceID,
          targetID: connector.targetID,
          type: connector.type,
          style_strokeColor: connector.style.strokeColor,
          sourcePoint_x: connector.sourcePoint.x,
          sourcePoint_y: connector.sourcePoint.y,
          targetPoint_x: connector.targetPoint.x,
          targetPoint_y: connector.targetPoint.y
        })
      }
      this.$http({
        method: 'POST',
        url: '/episode/api/system/workflow',
        data: {wfProcess: this.listGrid.currentData, nodes: nodes, connectors: connectors}
      }).then(res => {
        this.listGrid.currentData = res.data.wfProcess
        // this.nodes = res.data.nodes
        // this.connectors = res.data.connectors
        this.$util.esmessage(this, 'check', this.labels.saveMessage)
      }).catch(error => {
        this.$util.esmessage(this, 'warn', this.labels.failMessage)
        console.log(error)
      })
    },
    del: function () {
    },
    handleRowClick: function (row, column, event) {
      this.activeTab = 'detail'
      this.listGrid.rowIndex = this.listGrid.filteredData.indexOf(row)
      this.getRowData(row.id)
    },
    getRowData: function (id) {
      this.$http.get('/episode/api/system/workflow/' + id).then(res => {
        this.listGrid.currentData = res.data.wfProcess
        let nodes = []
        for (let node of res.data.nodes) {
          nodes.push({
            id: node.wfnodeId,
            offsetX: node.offsetX,
            offsetY: node.offsetY,
            constraints: node.constraints,
            style: {
              fill: node.style_fill
            },
            annotations: [{content: node.annotations_content}],
            shape: {
              type: node.shape_type,
              shape: node.shape_shape
            }
          })
        }
        this.nodes = nodes
        let connectors = []
        for (let connector of res.data.connectors) {
          connectors.push({
            id: connector.wfconnectorId,
            sourceID: connector.sourceID,
            targetID: connector.targetID,
            type: connector.type,
            style: {
              strokeColor: connector.style_strokeColor
            },
            sourcePoint: {
              x: connector.sourcePoint_x,
              y: connector.sourcePoint_y
            },
            targetPoint: {
              x: connector.targetPoint_x,
              y: connector.targetPoint_y
            }
          })
        }
        this.connectors = connectors
        this.$http.get(
          '/episode/api/system/workflow/action?objectId=' + this.listGrid.currentData.objectId
        ).then(res => {
          this.actionList = res.data
        })
      })
    },
    dateTime: function (date) {
      return this.$util.dateTimeWithTimeZone(date)
    },
    handleAuthSelectionChange: function (val) {
      this.authSearchGrid.selection = val
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    exportFilter: function () {
      /*
      let lang = this.$store.getters.getUser.lang
      let fileName = 'Workflow'
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'orgId', label: this.labels.orgId},
        {value: 'username', label: this.labels.username},
        {value: 'firstname', label: this.labels.firstname},
        {value: 'status', label: this.labels.status},
        {value: 'createDt', label: this.labels.createDt},
        {value: 'createNm', label: this.labels.createId}
      ]

      if (lang === 'ko-kr') {
        fileName = this.labels.title
      }
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
      */
    },
    getListData: function () {
      this.$http.get(
        '/episode/api/system/workflow'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    created: function (args) {
      addEvents()
    },
    sourcePointChange: function (args) {
      if (args.state === 'Start') {
        this.startPoint = args.oldValue
      } else if (args.state === 'Completed') {
        let violated = false
        if (args.connector.sourceID !== '') {
          let connectors = this.$refs.diagram.ej2Instances.connectors
          let sourceNode = this.$refs.diagram.ej2Instances.nodes.filter(item => {
            return item.id === args.connector.sourceID
          })[0]
          let shape = sourceNode.shape.shape
          if (shape === 'Terminator') {
            if (sourceNode.id === 'Start') {
              violated = connectors.filter(item => {
                return item.sourceID === sourceNode.id
              }).length > 1 || args.connector.style.strokeColor === 'red'
            } else if (sourceNode.id === 'End') {
              violated = true
            }
          } else if (shape === 'Process') {
            violated = connectors.filter(item => {
              return item.sourceID === sourceNode.id
            }).length > 1 || args.connector.style.strokeColor === 'red'
          } else if (shape === 'Decision') {
            violated = connectors.filter(item => {
              return item.sourceID === sourceNode.id && item.style.strokeColor === args.connector.style.strokeColor
            }).length > 1
          } else if (shape === 'Data') {
            violated = args.connector.style.strokeColor === 'red'
          } else if (shape === 'Delay') {
            violated = connectors.filter(item => {
              return item.sourceID === sourceNode.id
            }).length > 1 || args.connector.style.strokeColor === 'red'
          }
        }

        if (args.connector.style.strokeColor === 'red') {
          args.connector.sourcePortID = 'downPort'
        } else {
          args.connector.sourcePortID = 'rightPort'
        }

        if (violated) {
          args.connector.sourceID = ''
          args.connector.sourcePoint.x = this.startPoint.x
          args.connector.sourcePoint.y = this.startPoint.y
        }
      }
    },
    targetPointChange: function (args) {
      if (args.state === 'Start') {
        this.startPoint = args.oldValue
      } else if (args.state === 'Completed') {
        if (args.connector.targetID === 'Start') {
          args.connector.targetID = ''
          args.connector.targetPoint.x = this.startPoint.x
          args.connector.targetPoint.y = this.startPoint.y
        }

        args.connector.targetPortID = 'leftPort'
      }
    },
    dragSourceEnd: function (args) {
      console.log('dragSourceEnd', args)
    },
    contextMenuOpen: function (args) {
      if (this.actualObject === undefined) {
        args.hiddenItems.push('diagram_contextMenu_properties')
      } else {
        if (this.actualObject.constructor.name === 'Node') {
          if (this.actualObject.id === 'Start') {
            args.hiddenItems.push('diagram_contextMenu_cut')
            args.hiddenItems.push('diagram_contextMenu_properties')
          }
          if (this.actualObject.id === 'End') {
            args.hiddenItems.push('diagram_contextMenu_cut')
            args.hiddenItems.push('diagram_contextMenu_properties')
          }

          if (this.sourceObject === null) {
          }
        }
        if (this.actualObject.constructor.name === 'Connector') {
          // args.hiddenItems.push('diagram_contextMenu_copy')
        }
      }
      // args.hiddenItems.push('diagram_contextMenu_selectAll')
      // args.hiddenItems.push('diagram_contextMenu_order')
      // args.hiddenItems.push('diagram_contextMenu_paste')
      // args.hiddenItems.push('diagram_contextMenu_copy')
      /*
      console.log('contextMenuOpen', this.$refs.diagram.ej2Instances)
      for (let item of args.items) {
        if (item.text === 'delete') {
          if (!this.refs.diagram.selectedItems.nodes.length && !this.refs.diagram.selectedItems.connectors.length) {
            args.hiddenItems.push(item.text)
          }
        }
      }
      */
    },
    selectionChange: function (args) {
      if (args.state === 'Changed') {
        this.selectedItems = args.newValue
      }
    },
    click: function (args) {
      if (this.actualObject !== undefined) {
        // console.log(args.actualObject)
      }
      // this.nodes.push(newNode)
      // console.log('instance connector', this.$refs.diagram.ej2Instances.connectors)
      /*
      let nodes = this.$refs.diagram.ej2Instances.nodes
      console.log('instances nodes')
      let node
      for (node of nodes) {
        console.log(node.id, node.offsetX, node.offsetY, node.shape, node.annotations, node.style, node.constraints)
      }
      console.log('nodes')
      nodes = this.nodes
      for (node of nodes) {
        console.log(node.id, node.offsetX, node.offsetY, node.shape, node.annotations, node.style, node.constraints)
      }
      */
      // console.log('diagram', this.$refs.diagram.ej2Instances.nodes)
      this.actualObject = args.actualObject
    },
    contextMenuBeforeItemRender: function (args) {
      // console.log('contextMenuBeforeItemRender', args)
    },
    contextMenuClick: function (args) {
      if (args.item.id === 'diagram_contextMenu_properties') {
        console.log('actualObject ', this.actualObject)
        console.log('hasOwnProperty @@ ', this.actualObject.type)
        // let type = this.actualObject.constructor.name === 'Node' ? 'node' : 'connector'
        let type = this.actualObject.type === undefined ? 'node' : 'connector'
        this.$http.get(
          '/episode/api/system/workflow/' + this.listGrid.currentData.id + '/' + type + '/', {params: {elementId: this.actualObject.id}}
        ).then(res => {
          if (res.data.id !== undefined) {
            this.properties = res.data
            this.propertiesVisible = true
          } else {
            this.$util.esmessage(this, 'warn', this.labels.mustSave)
          }
        })
      }
      /*
      if (args.item.id === 'diagram_contextMenu_remove') {
        this.actualObject = null
        this.sourceObject = null
        this.targetObject = null
      }
      */
    }
  },
  data () {
    return {
      actionList: [],
      tableList: [],
      assignmentList: [],
      statusList: [],
      properties: {
        id: '',
        wfnodeId: '',
        actionId: ''
      },
      propertiesVisible: false,
      validMsg: {
        workflowIdMsg: '',
        descriptionMsg: ''
      },
      startPoint: {x: 0, y: 0},
      actualObject: undefined,
      sourceObject: undefined,
      targetObject: undefined,
      selectedItems: [],
      contextMenuSettings: {
        show: true,
        showCustomMenuOnly: true,
        items: [
          {text: 'Properties', id: 'diagram_contextMenu_properties', target: '.e-elementcontent', iconCss: 'e-load'},
          {text: 'Delete', id: 'diagram_contextMenu_cut', target: '.e-elementcontent', iconCss: 'e-delete'}
        ]
        /*
        showCustomMenuOnly: false
        items: [
          {text: 'Delete', id: 'delete', target: '.e-elementcontent', iconCss: 'e-delete'}
          {text: 'Save', id: 'save', target: '.e-elementcontent', iconCss: 'e-save'},
          {text: 'Load', id: 'load', target: '.e-elementcontent', iconCss: 'e-load'},
          {text: 'Delete', id: 'delete', target: '.e-elementcontent', iconCss: 'e-delete'},
          {text: 'Clear', id: 'clear', target: '.e-elementcontent', iconCss: 'e-clear'}
        ]
        */
      },
      activeTab: 'list',
      labels: {
        title: '',
        delete: '',
        save: '',
        close: '',
        new: '',
        list: '',
        detail: '',
        datafilter: '',
        id: '',
        username: '',
        status: '',
        description: '',
        appId: '',
        datafilterId: '',
        whereClause: '',
        searchData: '',
        selectData: '',
        saveMessage: '',
        failMessage: '',
        userfitlerMessage: '',
        deleteMessage: '',
        search: '',
        excelDownload: '',
        deleteall: '',
        deleteselected: '',
        deleteAll: '',
        deleteSelect: '',
        multipleAdd: '',
        inputDialogTitle: '',
        add: '',
        createId: '',
        createDt: '',
        updateId: '',
        updateDt: '',
        address: '',
        option: '',
        deleteSuccess: '',
        selectNullMsg: '',
        chkChgDataMsg: ''
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 20, 30]
        },
        data: [],
        currentData: {
          nodes: [],
          connectors: []
        },
        pageIndex: 1,
        rowIndex: -1,
        filters: [{
          prop: ['wfprocessId', 'description'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'id',
          width: '120px'
        }, {
          prop: 'orgId',
          width: '180px'
        }, {
          prop: 'username',
          width: '250px'
        }, {
          prop: 'firstname',
          width: '200px'
        }, {
          prop: 'status',
          width: '200px'
        }, {
          prop: 'createDt',
          width: '250px'
        }, {
          prop: 'createId',
          width: '150px'
        }],
        columns: ['id', 'orgId', 'username', 'firstname', 'status', 'createDt', 'createId'],
        columnNames: ['id', 'orgId', 'username', 'firstname', 'status', 'createDt', 'createId'],
        searchParam: ''
      },
      width: '100%',
      height: '626px',
      nodes: [],
      connectors: [],
      snapSettings: {
        horizontalGridlines: config.gridlines,
        verticalGridlines: config.gridlines
      },
      expandMode: 'Multiple',
      palettes: [
        {
          id: 'flow',
          expanded: true,
          symbols: config.flowshapes,
          iconCss: 'shapes',
          title: 'Nodes'
          // title: 'Flow Shapes'
        },
        {
          id: 'connectors',
          expanded: true,
          symbols: config.connectorSymbols,
          iconCss: 'shapes',
          title: 'Connectors'
        }
      ],
      palettewidth: '100%',
      paletteheight: '700px',
      symbolHeight: 60,
      symbolWidth: 60,
      symbolMargin: { left: 15, right: 15, top: 15, bottom: 15 },
      getNodeDefaults: (node) => {
        let obj = {}
        obj.width = 60
        obj.height = obj.width * 0.6
        obj.style = { fill: '#357BD2', strokeColor: 'white' }
        obj.annotations = [{ style: { color: 'white', fill: 'transparent' } }]
        // obj.ports = getPorts()
        let rightPort = {id: 'rightPort', shape: 'Circle', offset: { x: 1, y: 0.5 }}
        let leftPort = {id: 'leftPort', shape: 'Circle', offset: { x: 0, y: 0.5 }}
        let downPort = {id: 'downPort', shape: 'Circle', offset: { x: 0.5, y: 1 }}
        obj.ports = []
        if (node.shape.shape === 'Terminator') {
          if (node.id === 'Start') {
            obj.ports.push(rightPort)
          } else if (node.id === 'End') {
            obj.ports.push(leftPort)
          }
        } else if (node.shape.shape === 'Decision') {
          obj.ports.push(rightPort)
          obj.ports.push(leftPort)
          obj.ports.push(downPort)
        } else {
          obj.ports.push(rightPort)
          obj.ports.push(leftPort)
        }
        return obj
      },
      getConnectorDefaults: (obj) => {
        if (obj.id.indexOf('connector') !== -1) {
          obj.type = 'Orthogonal'
          // obj.targetDecorator = { shape: 'Arrow', width: 10, height: 10 }
        }
        obj.sourcePortID = obj.style.strokeColor === 'red' ? 'downPort' : 'rightPort'
        obj.targetPortID = 'leftPort'
      },
      dragEnter: (args) => {
        let obj = args.element
        if (obj instanceof Node && obj.width && obj.height) {
          let oWidth = obj.width
          let oHeight = obj.height
          let ratio = 100 / obj.width
          obj.width = 70
          obj.height *= ratio
          if (obj.offsetX) {
            obj.offsetX += (obj.width - oWidth) / 2
          }
          if (obj.offsetY) {
            obj.offsetY += (obj.height - oHeight) / 2
          }
          obj.style = { fill: '#357BD2', strokeColor: 'white' }
        }
        obj.width = 60
        obj.height = obj.width * 0.6
        if (obj.constructor.name === 'Node') {
          obj.constraints = NodeConstraints.Default & ~NodeConstraints.Rotate & ~NodeConstraints.Resize
        }
      },
      palettegetNodeDefaults: (symbol) => {
        if (symbol.id === 'Terminator' || symbol.id === 'Process') {
          symbol.width = 80
          symbol.height = 40
        } else if (
          symbol.id === 'Decision' ||
          symbol.id === 'Document' ||
          symbol.id === 'PreDefinedProcess' ||
          symbol.id === 'PaperTap' ||
          symbol.id === 'DirectData' ||
          symbol.id === 'MultiDocument' ||
          symbol.id === 'Data'
        ) {
          symbol.width = 50
          symbol.height = 40
        } else {
          symbol.width = 50
          symbol.height = 50
        }
        symbol.width = 80
      },
      getSymbolInfo: (symbol) => {
        return { fit: true }
      }
    }
  },
  mounted: function () {
    this.initialize()
    this.getListData()
  }
}
const INIT_NODES = [
  {
    id: 'Start',
    offsetX: 150,
    offsetY: 200,
    shape: { type: 'Flow', shape: 'Terminator' },
    annotations: [{content: 'Start'}],
    style: { fill: '#65B091' },
    constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate & ~NodeConstraints.Delete
  },
  {
    id: 'End',
    offsetX: 650,
    offsetY: 200,
    shape: { type: 'Flow', shape: 'Terminator' },
    style: { fill: '#D5535D' },
    annotations: [{content: 'End'}],
    constraints: NodeConstraints.Default & ~NodeConstraints.Resize & ~NodeConstraints.Rotate & ~NodeConstraints.Delete
  }
]
/*
function getPorts () {
  let ports = [
    { id: 'port1', shape: 'Circle', offset: { x: 0, y: 0.5 } },
    { id: 'port2', shape: 'Circle', offset: { x: 0.5, y: 1 } },
    { id: 'port3', shape: 'Circle', offset: { x: 1, y: 0.5 } },
    { id: 'port4', shape: 'Circle', offset: { x: 0.5, y: 0 } }
  ]
  return ports
}
*/
function addEvents () {
  isMobile = window.matchMedia('(max-width:550px)').matches
  if (isMobile) {
    let paletteIcon = document.getElementById('palette-icon')
    if (paletteIcon) {
      paletteIcon.addEventListener('click', openPalette, false)
    }
  }
}

function openPalette () {
  let paletteSpace = document.getElementById('palette-space')
  isMobile = window.matchMedia('(max-width:550px)').matches
  if (isMobile) {
    if (!paletteSpace.classList.contains('sb-mobile-palette-open')) {
      paletteSpace.classList.add('sb-mobile-palette-open')
    } else {
      paletteSpace.classList.remove('sb-mobile-palette-open')
    }
  }
}
</script>
<style>
  @import '../../../assets/css/workflow.css';
  @import "@syncfusion/ej2-base/styles/material.css";
  @import "@syncfusion/ej2-buttons/styles/material.css";
  @import "@syncfusion/ej2-inputs/styles/material.css";
  @import "@syncfusion/ej2-popups/styles/material.css";
  @import "@syncfusion/ej2-navigations/styles/material.css";

#target {
    border: 1px dashed;
    height: 150px;
    padding: 10px;
    position: relative;
    text-align: justify;
    color: gray;
    user-select: none;
}
</style>
