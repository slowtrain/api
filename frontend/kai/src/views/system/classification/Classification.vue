<!--
 Class Name  : Classification.vue
 Description : 분류
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.19  최영훈  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class="comm-btn-box" role="group">
            <b-button variant="primary" class="px-3 sm" v-show="visibleDelete"  v-on:click="del">{{labels.delete}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleSave" v-on:click="save">{{labels.save}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-show="visibleAdd" v-on:click="add">{{labels.new}}</b-button>
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
      <b-row>
        <b-col sm="3">
          <Tree
            v-if="treeFlag"
            v-bind:tree="tree"
            ref="tree"
            v-on:clickNode="clickNode"
            v-on:clickAddBtn="clickAddBtn"
          />
        </b-col>
        <b-col sm="9">
          <!--LIST-->
          <el-tabs v-model="activeTab" :before-leave="chgTab">
            <el-tab-pane  name="list" :label='labels.list'>
                <b-row>
                  <div class="col-md-6" style="padding-top:10px; padding-left:20px;">
                    <div class="search-component sm">
                      <input class="form-control formdata sm" style="width: 80%;" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder=labels.searchA />
                    </div>
                  </div>
                  <div class='col-md-6'>
                    <div class="float-right mt-1" role="group">
                      <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                        <img src="../../../assets/img/icons/icon_search_download.png" width="60%" />
                      </el-button>
                    </div>
                  </div>
                </b-row>
                <b-row class="mt-2">
                  <div class='col'>
                    <p class="grid-line"></p>
                    <data-tables v-if="listGridFlag" style="width: 100%" layout='table, pagination' :current-page.sync='listGrid.currentPage' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps' v-on:row-click="clickGridRow" v-on:filtered-data="handleFilteredData">
                      <el-table-column :prop='listGrid.titles[0].prop' :label='labels.id'               sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
                      <el-table-column :prop='listGrid.titles[2].prop' :label='labels.parent'           sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                      <el-table-column :prop='listGrid.titles[3].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
                      <el-table-column :prop='listGrid.titles[4].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                      <el-table-column :prop='listGrid.titles[5].prop' :label='labels.description'      sortable='custom' ></el-table-column>
                    </data-tables>
                  </div>
                </b-row>
            </el-tab-pane>
            <!--DETAIL-->
            <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
              <b-row>
                <div class="form-group col-md-2">
                  <label for="id">{{labels.id}}</label>
                  <input id="id" :placeholder="'ID'" v-model="listGrid.currentData.id" class="formdata sm" autocomplete="off" disabled="disabled" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="parent">{{labels.parent}}</label>
                  <div class="search-component sm">
                    <input id="parent" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.parent" class="formdata sm" autocomplete="off" readonly/>
                    <a class="search-button cursor-pointer" v-on:click="openParentDialog">search</a>
                  </div>
                </div>
                <div class="form-group col-md-3">
                  <label for="classstructureId">{{labels.classstructureId}}<span class="require-mark">*</span></label>
                  <input id="classstructureId" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.classstructureId" v-on:blur="getClassstructureIdChk" class="formdata sm" autocomplete="off" v-bind:disabled="disabledClass" v-bind:maxlength="30" v-on:change="changeValue(listGrid.currentData)"/>
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.classstructureIdMsg)" v-text="validMsg.classstructureIdMsg"></p>
                </div>
                <div class="form-group col-md-4">
                  <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
                  <input id="description" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.description" class="formdata sm" autocomplete="off" v-bind:maxlength="50" v-on:change="changeValue(listGrid.currentData)"/>
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-2">
                  <label for="orgId">{{labels.orgId}}</label>
                  <el-select v-model="listGrid.currentData.orgId" :placeholder="labels.selectData" disabled="disabled" v-on:change="changeValue(listGrid.currentData)">
                    <el-option v-for="org in orgSelect" :prop="org.orgId" :value="org.orgId" :key="org.orgId">
                      {{org.orgId}}
                    </el-option>
                  </el-select>
                </div>
                <div class="form-group col-md-3">
                  <label for="status">{{labels.status}}</label>
                  <el-select v-model="listGrid.currentData.status" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
                    <el-option v-for="status in statusSelect" :prop="status.value" :value="status.value" :key="status.key">
                      {{status.value}}
                    </el-option>
                  </el-select>
                </div>
                <div class="form-group col-md-7">
                  <label for="hierarchypath">{{labels.hierarchypath}}</label>
                  <input id="hierarchypath" :placeholder="'PATH'" v-model="listGrid.currentData.hierarchypath" class="formdata sm" autocomplete="off" v-bind:disabled="true" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-10">
                  <label for="classiconsvg">{{labels.classiconsvg}}</label>
                  <i class="class-svg" v-html="listGrid.currentData.classiconsvg"></i>
                  <input id="classiconsvg"  :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.classiconsvg" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-2">
                  <label for="datasrc">{{labels.datasrc}}</label>
                  <input id="datasrc" v-model="listGrid.currentData.datasrc" class="formdata sm" autocomplete="off" disabled = 'disabled'/>
                </div>
              </b-row>
              <b-row>
                <div class='form-group col-md-3'>
                  <label for="application_createId">{{labels.createId}}</label>
                  <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
                </div>
                <div class='form-group col-md-3'>
                  <label for="application_createDt">{{labels.createDt}}</label>
                  <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                </div>
                <div class='form-group col-md-3'>
                  <label for="application_updateId">{{labels.updateId}}</label>
                  <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
                </div>
                <div class='form-group col-md-3'>
                  <label>{{labels.updateDt}}</label>
                  <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                </div>
              </b-row>
              <!-- 문자 속성 -->
              <b-row class='mt-2'>
                <div class="col" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <b-row>
                <div class="col" style="padding-top:20px; padding-left:20px;">
                  <a class="title-box">{{labels.extaln}}</a>
                </div>
              </b-row>
              <b-row class="mt-3">
                <div class="form-group col-md-3">
                  <label for="extaln1">{{labels.extaln1}}</label>
                  <input id="extaln1" v-model="listGrid.currentData.extaln1" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln2">{{labels.extaln2}}</label>
                  <input id="extaln2" v-model="listGrid.currentData.extaln2" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln3">{{labels.extaln3}}</label>
                  <input id="extaln3" v-model="listGrid.currentData.extaln3" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln4">{{labels.extaln4}}</label>
                  <input id="extaln4" v-model="listGrid.currentData.extaln4" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-3">
                  <label for="extaln5">{{labels.extaln5}}</label>
                  <input id="extaln5" v-model="listGrid.currentData.extaln5" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln6">{{labels.extaln6}}</label>
                  <input id="extaln6" v-model="listGrid.currentData.extaln6" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln7">{{labels.extaln7}}</label>
                  <input id="extaln7" v-model="listGrid.currentData.extaln7" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln8">{{labels.extaln8}}</label>
                  <input id="extaln8" v-model="listGrid.currentData.extaln8" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-3">
                  <label for="extaln9">{{labels.extaln9}}</label>
                  <input id="extaln9" v-model="listGrid.currentData.extaln9" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln10">{{labels.extaln10}}</label>
                  <input id="extaln10" v-model="listGrid.currentData.extaln10" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln11">{{labels.extaln11}}</label>
                  <input id="extaln11" v-model="listGrid.currentData.extaln11" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln12">{{labels.extaln12}}</label>
                  <input id="extaln12" v-model="listGrid.currentData.extaln12" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-3">
                  <label for="extaln13">{{labels.extaln13}}</label>
                  <input id="extaln13" v-model="listGrid.currentData.extaln13" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln14">{{labels.extaln14}}</label>
                  <input id="extaln14" v-model="listGrid.currentData.extaln14" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln15">{{labels.extaln15}}</label>
                  <input id="extaln15" v-model="listGrid.currentData.extaln15" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln16">{{labels.extaln16}}</label>
                  <input id="extaln16" v-model="listGrid.currentData.extaln16" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-3">
                  <label for="extaln17">{{labels.extaln17}}</label>
                  <input id="extaln17" v-model="listGrid.currentData.extaln17" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln18">{{labels.extaln18}}</label>
                  <input id="extaln18" v-model="listGrid.currentData.extaln18" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln19">{{labels.extaln19}}</label>
                  <input id="extaln19" v-model="listGrid.currentData.extaln19" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extaln20">{{labels.extaln20}}</label>
                  <input id="extaln20" v-model="listGrid.currentData.extaln20" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <!-- 숫자 속성 -->
              <b-row class='mt-2'>
                <div class="col" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <b-row>
                <div class="col" style="padding-top:20px; padding-left:20px;">
                  <a class="title-box">{{labels.extnum}}</a>
                </div>
              </b-row>
              <b-row class="mt-3">
                <div class="form-group col-md-3">
                  <label for="extnum1">{{labels.extnum1}}</label>
                  <input id="extnum1" v-model="listGrid.currentData.extnum1" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extnum2">{{labels.extnum2}}</label>
                  <input id="extnum2" v-model="listGrid.currentData.extnum2" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extnum3">{{labels.extnum3}}</label>
                  <input id="extnum3" v-model="listGrid.currentData.extnum3" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extnum4">{{labels.extnum4}}</label>
                  <input id="extnum4" v-model="listGrid.currentData.extnum4" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extnum5">{{labels.extnum5}}</label>
                  <input id="extnum5" v-model="listGrid.currentData.extnum5" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <!-- 날짜 속성 -->
              <b-row class='mt-2'>
                <div class="col" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <b-row>
                <div class="col" style="padding-top:20px; padding-left:20px;">
                  <a class="title-box">{{labels.extdate}}</a>
                </div>
              </b-row>
              <b-row class="mt-3">
                <div class="form-group col-md-3">
                  <label for="extdate1">{{labels.extdate1}}</label>
                  <input id="extdate1" v-model="listGrid.currentData.extdate1" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extdate2">{{labels.extdate2}}</label>
                  <input id="extdate2" v-model="listGrid.currentData.extdate2" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extdate3">{{labels.extdate3}}</label>
                  <input id="extdate3" v-model="listGrid.currentData.extdate3" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extdate4">{{labels.extdate4}}</label>
                  <input id="extdate4" v-model="listGrid.currentData.extdate4" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extdate5">{{labels.extdate5}}</label>
                  <input id="extdate5" v-model="listGrid.currentData.extdate5" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <!-- BOOLEAN 속성 -->
              <b-row class='mt-2'>
                <div class="col" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <b-row>
                <div class="col" style="padding-top:20px; padding-left:20px;">
                  <a class="title-box">{{labels.extbool}}</a>
                </div>
              </b-row>
              <b-row class="mt-3">
                <div class="form-group col-md-3">
                  <label for="extbool1">{{labels.extbool1}}</label>
                  <input id="extbool1" v-model="listGrid.currentData.extbool1" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extbool2">{{labels.extbool2}}</label>
                  <input id="extbool2" v-model="listGrid.currentData.extbool2" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extbool3">{{labels.extbool3}}</label>
                  <input id="extbool3" v-model="listGrid.currentData.extbool3" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extbool4">{{labels.extbool4}}</label>
                  <input id="extbool4" v-model="listGrid.currentData.extbool4" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="extbool5">{{labels.extbool5}}</label>
                  <input id="extbool5" v-model="listGrid.currentData.extbool5" class="formdata sm" autocomplete="off" v-bind:maxlength="20" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
            </el-tab-pane>
            <!--SPEC-->
            <el-tab-pane  name="spec" :label='labels.classspecId' v-if="(activeTab !== 'list')">
              <b-row>
                <div class="form-group col-md-2">
                  <label for="id">{{labels.id}}</label>
                  <input id="id" :placeholder="'ID'" v-model="listGrid.currentData.id" class="formdata sm" autocomplete="off" disabled="disabled" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-3">
                  <label for="parent">{{labels.parent}}</label>
                  <div class="search-component sm">
                    <input id="parent" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.parent" class="formdata sm" autocomplete="off" readonly/>
                    <a class="search-button cursor-pointer" v-on:click="openParentDialog">search</a>
                  </div>
                </div>
                <div class="form-group col-md-3">
                  <label for="classstructureId">{{labels.classstructureId}}<span class="require-mark">*</span></label>
                  <input id="classstructureId" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.classstructureId" v-on:blur="getClassstructureIdChk" class="formdata sm" autocomplete="off" v-bind:disabled="disabledClass" v-bind:maxlength="30" v-on:change="changeValue(listGrid.currentData)"/>
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.classstructureIdMsg)" v-text="validMsg.classstructureIdMsg"></p>
                </div>
                <div class="form-group col-md-4">
                  <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
                  <input id="description" :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.description" class="formdata sm" autocomplete="off" v-bind:maxlength="50" v-on:change="changeValue(listGrid.currentData)"/>
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-2">
                  <label for="orgId">{{labels.orgId}}</label>
                  <el-select v-model="listGrid.currentData.orgId" :placeholder="labels.selectData" disabled="disabled" v-on:change="changeValue(listGrid.currentData)">
                    <el-option v-for="org in orgSelect" :prop="org.orgId" :value="org.orgId" :key="org.orgId">
                      {{org.orgId}}
                    </el-option>
                  </el-select>
                </div>
                <div class="form-group col-md-3">
                  <label for="status">{{labels.status}}</label>
                  <el-select v-model="listGrid.currentData.status" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
                    <el-option v-for="status in statusSelect" :prop="status.value" :value="status.value" :key="status.key">
                      {{status.value}}
                    </el-option>
                  </el-select>
                </div>
                <div class="form-group col-md-7">
                  <label for="hierarchypath">{{labels.hierarchypath}}</label>
                  <input id="hierarchypath" :placeholder="'PATH'" v-model="listGrid.currentData.hierarchypath" class="formdata sm" autocomplete="off" v-bind:disabled="true" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-10">
                  <label for="classiconsvg">{{labels.classiconsvg}}</label>
                  <i class="class-svg" v-html="listGrid.currentData.classiconsvg"></i>
                  <input id="classiconsvg"  :placeholder="labels.inputDataMsg" v-model="listGrid.currentData.classiconsvg" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
                </div>
                <div class="form-group col-md-2">
                  <label for="datasrc">{{labels.datasrc}}</label>
                  <input id="datasrc" v-model="listGrid.currentData.datasrc" class="formdata sm" autocomplete="off" disabled = 'disabled'/>
                </div>
              </b-row>
              <b-row>
                <div class='form-group col-md-3'>
                  <label for="application_createId">{{labels.createId}}</label>
                  <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
                </div>
                <div class='form-group col-md-3'>
                  <label for="application_createDt">{{labels.createDt}}</label>
                  <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
                </div>
                <div class='form-group col-md-3'>
                  <label for="application_updateId">{{labels.updateId}}</label>
                  <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
                </div>
                <div class='form-group col-md-3'>
                  <label>{{labels.updateDt}}</label>
                  <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                </div>
              </b-row>
              <b-row class='mt-2'>
                <div class="col" style="border: 0.5px solid #c8d3df;"></div>
              </b-row>
              <b-row class='mt-2'>
                <!--SPEC LIST-->
                <div class="col-md-6">
                  <b-row>
                    <div class="col" style="padding-top:20px; padding-left:20px;">
                      <a class="title-box">{{labels.classspecId}}</a>
                    </div>
                  </b-row>
                  <b-row>
                    <div class="col" style="padding-top:18px; padding-left:20px;">
                      <div class="search-component sm">
                        <input class="form-control formdata sm" style="width: 100%;" v-model="specGrid.searchParam" v-on:keyup.enter="enterSearchSpec" :placeholder=labels.searchA />
                      </div>
                    </div>
                    <div class="col">
                      <div class="float-right mt-3" role="group">
                        <b-button variant="primary" class="px-3 sm" v-on:click="addSpec" v-if="visibleAddSpec">{{labels.new}}</b-button>
                      </div>
                    </div>
                  </b-row>
                  <b-row class='mt-2'>
                    <div class="form-group col">
                      <p class='grid-line'></p>
                      <data-tables layout='table, pagination' :current-page.sync='specGrid.currentPage' :data='specGrid.data' :filters='specGrid.filters' :page-size='specGrid.pageProps.pageSize' :pagination-props='specGrid.pageProps'>
                        <el-table-column prop="classspecId" :label="labels.classspecId" >
                          <template slot-scope="props">
                            <input class="form-control formdata sm" type="text" v-model="props.row.classspecId" :disabled="props.row.hasOwnProperty('id')" v-on:blur="getClassspecIdChk(props.row)" v-on:change="changeValue(props.row)" v-bind:maxlength="30"/>
                          </template>
                        </el-table-column>
                        <el-table-column prop="description" :label="labels.description" >
                          <template slot-scope="props">
                            <input class="form-control formdata sm" type="text" v-model="props.row.description" v-on:change="changeValue(props.row)" v-bind:maxlength="50"/>
                          </template>
                        </el-table-column>
                        <el-table-column prop="measureunit" :label="labels.measureunit" >
                          <template slot-scope="props">
                            <el-select v-model="props.row.measureunit" :placeholder="labels.selectData" v-on:change="changeValue(props.row)">
                              <el-option v-for="measureunit in measureunitSelect" :prop="measureunit.key" :value="measureunit.value" :key="measureunit.key">
                                {{measureunit.value}}
                              </el-option>
                            </el-select>
                          </template>
                        </el-table-column>
                        <el-table-column prop="type" :label="labels.type" >
                          <template slot-scope="props">
                            <el-select v-model="props.row.type" :placeholder="labels.selectData" v-on:change="changeValue(props.row)">
                              <el-option v-for="spectype in spectypeSelect" :prop="spectype.key" :value="spectype.key"  :label="spectype.value" :key="spectype.key"></el-option>
                            </el-select>
                          </template>
                        </el-table-column>
                        <el-table-column align="right" width="50">
                          <template slot-scope="props">
                            <el-button v-show="visibleDeleteSpec" v-on:click="delSpec(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                              <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                            </el-button>
                          </template>
                        </el-table-column>
                      </data-tables>
                    </div>
                  </b-row>
                </div>
                <!--USEWITH LIST-->
                <div class="col-md-6">
                  <b-row>
                    <div class="col" style="padding-top:20px; padding-left:20px;">
                      <a class="title-box">{{labels.usewith}}</a>
                    </div>
                  </b-row>
                  <b-row>
                    <div class="col" style="padding-top:18px; padding-left:20px;">
                      <div class="search-component sm">
                        <input class="form-control formdata sm" style="width: 100%;" v-model="usewithGrid.searchParam" v-on:keyup.enter="enterSearchUsewith" :placeholder=labels.searchA />
                      </div>
                    </div>
                    <div class="col">
                      <div class="float-right mt-3" role="group">
                        <b-button variant="primary" class="px-3 sm" v-on:click="addUsewith" v-if="visibleAddUsewith">{{labels.new}}</b-button>
                      </div>
                    </div>
                  </b-row>
                  <b-row class='mt-2'>
                    <div class="form-group col">
                      <p class='grid-line'></p>
                      <data-tables layout='table, pagination' :current-page.sync='usewithGrid.currentPage' :data='usewithGrid.data' :filters='usewithGrid.filters' :page-size='usewithGrid.pageProps.pageSize' :pagination-props='usewithGrid.pageProps'>
                        <el-table-column prop="type" :label="labels.object" >
                          <template slot-scope="props">
                            <el-select v-model="props.row.object" :placeholder="labels.selectData" v-on:change="changeValue(props.row)" :disabled="props.row.hasOwnProperty('id')">
                              <el-option v-for="usewithTable in usewithTableSelect" :prop="usewithTable.value" :value="usewithTable.value" :key="usewithTable.key">
                                {{usewithTable.value}}
                              </el-option>
                            </el-select>
                          </template>
                        </el-table-column>
                        <el-table-column align="right" width="80">
                          <template slot-scope="props">
                            <el-button v-show="visibleDeleteUsewith" v-on:click="delUsewith(props.$index, props.row)" :title="labels.delete" style="padding: 0; width:30px; height: 30px;">
                              <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                            </el-button>
                          </template>
                        </el-table-column>
                      </data-tables>
                    </div>
                  </b-row>
                </div>
              </b-row>
            </el-tab-pane>
          </el-tabs>
        </b-col>
      </b-row>
      <!-- Parent Dialog -->
      <el-dialog :title="labels.parentDialogTitle" :visible.sync="showParentDialogFlag" width=290px>
        <b-row>
          <div class="col">
            <div class="treeDialog">
              <TreeParent v-bind:tree="treeParent" ref="treeParent"/>
            </div>
          </div>
        </b-row>
        <b-row>
          <div class="col mt-2">
            <div class='d-flex justify-content-end'>
              <b-button variant="primary" class="px-3 sm" v-on:click="confirmParentDialog">{{labels.confirm}}</b-button>
              <b-button variant="primary" class="px-3 sm" v-on:click="cancleParentDialog">{{labels.cancle}}</b-button>
            </div>
          </div>
        </b-row>
      </el-dialog>
    </b-card>
  </div>
</template>
<script>
import Tree from '@/views/common/TreeCom'
export default {
  name: 'CLASSIFICATION',
  components: {
    'Tree': Tree,
    'TreeParent': Tree
  },
  data () {
    return {
      labels: {
        title: '',
        root: '',
        list: '',
        detail: '',
        id: '',
        orgId: '',
        parent: null,
        classstructureId: '',
        status: '',
        description: '',
        hierarchypath: '',
        extaln1: '',
        extaln2: '',
        extaln3: '',
        extaln4: '',
        extaln5: '',
        extaln6: '',
        extaln7: '',
        extaln8: '',
        extaln9: '',
        extaln10: '',
        extnum1: '',
        extnum2: '',
        extnum3: '',
        extnum4: '',
        extnum5: '',
        extdate1: '',
        extdate2: '',
        extdate3: '',
        extdate4: '',
        extdate5: '',
        createDt: '',
        createId: '',
        updateDt: '',
        updateId: ''
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
          return true
        }
      },
      treeParent: {
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
        currentDataOld: {},
        total: 0,
        searchParam: ''
      },
      specGrid: {
        currentPage: 1,
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        currentData: {
        },
        data: [],
        filters: [{
          prop: [ 'classspecId', 'description' ],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'classspecId', label: 'SpecId' },
          { prop: 'description', label: 'Description' },
          { prop: 'measureunit', label: 'Measureunit' },
          { prop: 'type', label: 'Type' }
        ],
        columns: [
          'id',
          'orgId',
          'classstructureId',
          'classspecId',
          'description',
          'type',
          'measureunit'
        ],
        columnNames: [
          'id',
          'orgId',
          'classstructureId',
          'classspecId',
          'description',
          'type',
          'measureunit'
        ],
        searchParam: ''
      },
      usewithGrid: {
        currentPage: 1,
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10]
        },
        currentData: {
        },
        data: [],
        filters: [{
          prop: [ 'object' ],
          value: ''
        }],
        filteredData: [],
        titles: [
          { prop: 'object', label: 'object' }
        ],
        columns: [
          'id',
          'orgId',
          'classstructureId',
          'object'
        ],
        columnNames: [
          'id',
          'orgId',
          'classstructureId',
          'object'
        ],
        searchParam: ''
      },
      // Auth
      authorities: {
        CLASSIFICATION_ADD: false,
        CLASSIFICATION_SAVE: false,
        CLASSIFICATION_DELETE: false,
        CLASSIFICATION_SPEC_ADD: false,
        CLASSIFICATION_SPEC_SAVE: false,
        CLASSIFICATION_SPEC_DELETE: false,
        CLASSIFICATION_USEWITH_ADD: false,
        CLASSIFICATION_USEWITH_SAVE: false,
        CLASSIFICATION_USEWITH_DELETE: false
      },
      // Combo Data
      orgSelect: [],
      statusSelect: [],
      measureunitSelect: [],
      spectypeSelect: [],
      usewithTableSelect: [],
      classIconSelect: [],
      ancestor: [],
      // Global Var
      orgId: '',
      activeTab: 'list',
      // Global Flag
      showSvgIconFlag: false,
      showParentDialogFlag: false,
      showTreeMenu: false,
      validSpecFlag: false,
      validUseWithFlag: false,
      listGridFlag: false,
      treeFlag: false,
      // Global Msg
      validMsg: {
        classstructureIdMsg: '',
        descriptionMsg: ''
      }
    }
  },
  watch: {
    activeTab: function (val) {
      this.showParentDialogFlag = false
      this.validMsg.classstructureIdMsg = ''
      this.validMsg.descriptionMsg = ''
    },
    'listGrid.currentData.id': function (val) {
      this.showParentDialogFlag = false
      this.validMsg.classstructureIdMsg = ''
      this.validMsg.descriptionMsg = ''
    },
    'listGrid.currentData.classstructureId': function (val) {
      if (!this.$util.isNull(val) && this.$util.isNull(this.listGrid.currentData.id)) {
        this.listGrid.currentData.classstructureId = this.changeUpper(val)
        this.validMsg.classstructureIdMsg = ''
      }
    },
    'listGrid.currentData.description': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.descriptionMsg = ''
      }
    },
    'listGrid.currentData.parent': function (val) {
      if (this.$util.isNull(val)) {
        this.listGrid.currentData.parent = null
      }
    }
  },
  computed: {
    disabledClass: function () {
      return this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAdd: function () {
      return this.authorities.CLASSIFICATION_ADD && (this.activeTab === 'list')
    },
    visibleSave: function () {
      return this.authorities.CLASSIFICATION_SAVE && (this.activeTab !== 'list')
    },
    visibleDelete: function () {
      return this.authorities.CLASSIFICATION_DELETE && (this.activeTab !== 'list')
    },
    visibleAddSpec: function () {
      return this.authorities.CLASSIFICATION_SPEC_ADD && (this.activeTab !== 'list')
    },
    visibleDeleteSpec: function () {
      return this.authorities.CLASSIFICATION_SPEC_DELETE && (this.activeTab !== 'list')
    },
    visibleAddUsewith: function () {
      return this.authorities.CLASSIFICATION_USEWITH_ADD && (this.activeTab !== 'list')
    },
    visibleDeleteUsewith: function () {
      return this.authorities.CLASSIFICATION_USEWITH_DELETE && (this.activeTab !== 'list')
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.orgId = this.$store.getters.getUser.orgId
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'orgSelect', query: '/episode/api/system/common/organization'},
          {dataObj: 'statusSelect', domainId: 'CLASSSTATUS'},
          {dataObj: 'measureunitSelect', domainId: 'MEASUREUNIT'},
          {dataObj: 'spectypeSelect', domainId: 'SPECTYPE'},
          {dataObj: 'usewithTableSelect', domainId: 'CLASSUSEWITHTABLE'}
        ]
      }
      this.$util.getComboData(param)
      this.getTreeData()
      this.getListData()
      this.listGridFlag = true
      this.treeFlag = true
    },
    // USER FN ////////////////////////////////////////////
    makeHierarchypath: function () {
      let parent = this.listGrid.currentData.parent
      let classstructureId = this.listGrid.currentData.classstructureId
      let hierarchypath = ''
      if (!this.$util.isNull(parent)) {
        let data = this.treeParent.selectData
        hierarchypath = data.hierarchypath
      }
      this.listGrid.currentData.hierarchypath = hierarchypath + '/' + classstructureId
    },
    isValidationCheck: function () {
      let rtnFlag = true
      let classstructureId = this.listGrid.currentData.classstructureId
      let description = this.listGrid.currentData.description
      if (this.$util.isNull(classstructureId)) {
        this.validMsg.classstructureIdMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(description)) {
        this.validMsg.descriptionMsg = this.labels.inputData
        rtnFlag = false
      }
      return rtnFlag
    },
    changeUpper: function (val) {
      return val.toUpperCase()
    },
    // TRNS FN ////////////////////////////////////////////
    getTreeData: function (id) {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId
      ).then(res => {
        this.tree.data = res.data
        if (!this.$util.isNull(id)) {
          this.tree.expandKeys = [id]
          setTimeout(() => {
            this.$refs.tree.$refs.tree.setCurrentKey(id)
          }, 100)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function (id) {
      this.$http.get(
        '/episode/api/system/classification?orgId=' + this.orgId
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getRowData: function (id) {
      if (this.$util.isNull(id) || id === 'root') {
        return
      }
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      this.$http.get(
        '/episode/api/system/classification/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
        this.showSvgIconFlag = true
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/classification/' + id + '/ancestor'
      ).then(res => {
        this.ancestor = res.data
      }).catch(error => {
        console.log(error)
      })
      this.getCalssspec(id)
      this.getCalssusewith(id)
    },
    getCalssspec: function (id) {
      this.$http.get(
        '/episode/api/system/classification/' + id + '/spec'
      ).then(res => {
        this.specGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getCalssusewith: function (id) {
      this.$http.get(
        '/episode/api/system/classification/' + id + '/usewith'
      ).then(res => {
        this.usewithGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getClassstructureIdChk: function () {
      if (this.listGrid.currentData.classstructureId !== '') {
        this.$http.get(
          '/episode/api/system/classification?orgId=' + this.listGrid.currentData.orgId + '&classstructureId=' + this.listGrid.currentData.classstructureId
        ).then(res => {
          if (res.data.length > 0) {
            this.listGrid.currentData.classstructureId = ''
            this.validMsg.classstructureIdMsg = this.labels.classstructureIdExist
          } else {
            this.validMsg.classstructureIdMsg = ''
            this.makeHierarchypath()
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    save: function () {
      if (this.isValidationCheck()) {
        let specDataList = this.saveSpec()
        let usewithDataList = this.saveUsewith()
        if (this.validSpecFlag || this.validUseWithFlag) return
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/classification/' + this.listGrid.currentData.id : '/episode/api/system/classification'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || specDataList.length > 0 || usewithDataList.length > 0) {
          this.listGrid.currentData.specDataList = specDataList
          this.listGrid.currentData.usewithDataList = usewithDataList
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.getTreeData(res.data.id)
            this.getListData(res.data.id)
            this.getRowData(res.data.id)
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }
    },
    del: function () {
      if (this.listGrid.currentData.id === undefined || this.listGrid.currentData.id === '') {
        this.activeTab = 'list'
      } else if (this.ancestor.length > 1 || this.usewithGrid.data.length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.childDataExists)
      } else {
        let $this = this
        this.$util.esconfirm(this, 'delete', this.labels.confirmDelete, function () {
          this.$http.delete(
            '/episode/api/system/classification/' + $this.listGrid.currentData.id
          ).then(res => {
            $this.$util.esmessage($this, 'delete', $this.labels.deleteMessage)
            $this.activeTab = 'list'
            $this.listGrid.searchParam = ''
            $this.listGrid.filters[0].value = ''
            $this.listGrid.filters[1].value = ''
            $this.listGrid.currentData = {}
            $this.tree.expandKeys = ['root']
            $this.getTreeData()
            $this.getListData()
          }).catch(error => {
            $this.$util.esmessage($this, 'warn', $this.labels.failMessage)
            console.log(error)
          })
        })
      }
    },
    saveSpec: function () {
      this.validSpecFlag = false
      let dataList = this.specGrid.data.filter((data, index) => {
        return (data.state === 'ADDED' || data.state === 'CHANGED')
      })
      if (dataList.filter(data => (this.$util.isNull(data.classspecId))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.classspecIdMessage)
        this.validSpecFlag = true
        this.activeTab = 'spec'
        return []
      }
      if (dataList.filter(data => (this.$util.isNull(data.description))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.descriptionMessage)
        this.validSpecFlag = true
        this.activeTab = 'spec'
        return []
      }
      if (dataList.filter(data => (this.$util.isNull(data.measureunit))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.measureunitMessage)
        this.validSpecFlag = true
        this.activeTab = 'spec'
        return []
      }
      if (dataList.filter(data => (this.$util.isNull(data.type))).length > 0) {
        this.$util.esmessage(this, 'warn', this.labels.typeMessage)
        this.validSpecFlag = true
        this.activeTab = 'spec'
        return []
      }
      for (let i = 0; i < dataList.length; i++) {
        let classspec = dataList[i]
        if (this.specGrid.data.filter(classspecChk => classspec.classspecId === classspecChk.classspecId).length > 1) {
          this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
          this.validSpecFlag = true
          this.activeTab = 'spec'
          return []
        }
      }
      return dataList
    },
    delSpec: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/system/classification/spec/' + row.id).then(res => {
          this.specGrid.data.splice(index, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.specGrid.data.splice(index, 1)
      }
    },
    saveUsewith: function () {
      this.validUseWithFlag = false
      let dataList = this.usewithGrid.data.filter((data, index) => {
        return (data.state === 'ADDED' || data.state === 'CHANGED')
      })
      for (let i = 0; i < dataList.length; i++) {
        let usewith = dataList[i]
        if (this.usewithGrid.data.filter(usewithChk => usewith.object === usewithChk.object).length > 1) {
          this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
          this.validUseWithFlag = true
          this.activeTab = 'spec'
          return []
        }
      }
      return dataList
    },
    delUsewith: function (index, row) {
      if (row.state !== 'ADDED') {
        this.$http.delete('/episode/api/system/classification/usewith/' + row.id).then(res => {
          this.usewithGrid.data.splice(index, 1)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      } else {
        this.usewithGrid.data.splice(index, 1)
      }
    },
    // EVENT FN ///////////////////////////////////////////
    chgTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        this.listGrid.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.specGrid.searchParam = ''
        this.specGrid.filters[0].value = ''
        this.usewithGrid.searchParam = ''
        this.usewithGrid.filters[0].value = ''
        if (this.isChkChg()) {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.listGrid.currentData = {}
              $this.specGrid.data = []
              $this.usewithGrid.data = []
              $this.activeTab = 'list'
            })
        }
      }
      return rtnFlag
    },
    isChkChg: function () {
      let chgSpecDataList = this.specGrid.data.filter(data => (data.state === 'ADDED' || data.state === 'CHANGED'))
      let usewithDataList = this.usewithGrid.data.filter(data => (data.state === 'ADDED'))
      return this.listGrid.currentData.state === 'ADDED' || this.listGrid.currentData.state === 'CHANGED' || chgSpecDataList.length > 0 || usewithDataList.length > 0
    },
    exportFilter: function () {
      let fileName = 'classstructure'
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'parent', label: this.labels.parent},
        {value: 'classstructureId', label: this.labels.classstructureId},
        {value: 'status', label: this.labels.status},
        {value: 'description', label: this.labels.description}
      ]
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    },
    clickNode: function (node) {
      if (this.activeTab !== 'list') {
        if (this.isChkChg()) {
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              if (!$this.$util.isNull(node)) {
                $this.listGrid.searchParam = ''
                $this.listGrid.filters[0].value = ''
                $this.listGrid.filters[1].value = node.data
              }
              $this.getRowData(node.data.id)
            }
          )
          return
        }
      }
      if (!this.$util.isNull(node)) {
        this.listGrid.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.listGrid.filters[1].value = node.data
      }
      if (node.text === 'CLASSSTRUCTURE') {
        return
      }
      this.getRowData(node.data.id)
    },
    clickAddBtn: function (parent) {
      if (this.activeTab !== 'list') {
        if (this.isChkChg()) {
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.save()
            },
            function () {
              $this.addData(parent)
            }
          )
          return
        }
      }
      this.addData(parent)
    },
    addData: function (parent) {
      this.activeTab = 'detail'
      this.treeParent.selectData = parent
      this.listGrid.currentData = {
        orgId: 'KAI',
        classicon: 'stack-48.png',
        parent: parent.classstructureId === 'CLASSSTRUCTURE' ? null : parent.classstructureId,
        status: 'ACTIVE',
        hierarchypath: '',
        datasrc: 'EPISODE',
        extaln1: parent.extaln1,
        extaln2: parent.extaln2,
        extaln3: parent.extaln3,
        extaln4: parent.extaln4,
        extaln5: parent.extaln5,
        extaln6: parent.extaln6,
        extaln7: parent.extaln7,
        extaln8: parent.extaln8,
        extaln9: parent.extaln9,
        extaln10: parent.extaln10,
        extaln11: parent.extaln11,
        extaln12: parent.extaln12,
        extaln13: parent.extaln13,
        extaln14: parent.extaln14,
        extaln15: parent.extaln15,
        extaln16: parent.extaln16,
        extaln17: parent.extaln17,
        extaln18: parent.extaln18,
        extaln19: parent.extaln19,
        extaln20: parent.extaln20,
        extnum1: parent.extnum1,
        extnum2: parent.extnum2,
        extnum3: parent.extnum3,
        extnum4: parent.extnum4,
        extnum5: parent.extnum5,
        extdate1: parent.extdate1,
        extdate2: parent.extdate2,
        extdate3: parent.extdate3,
        extdate4: parent.extdate4,
        extdate5: parent.extdate5,
        extbool1: parent.extbool1,
        extbool2: parent.extbool2,
        extbool3: parent.extbool3,
        extbool4: parent.extbool4,
        extbool5: parent.extbool5
      }
      this.specGrid.data = []
      this.usewithGrid.data = []
    },
    clickGridRow: function (row, column, event) {
      this.activeTab = 'detail'
      this.tree.expandKeys = [row.id]
      this.$refs.tree.$refs.tree.setCurrentKey(row.id)
      this.getRowData(row.id)
    },
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    },
    add: function () {
      this.activeTab = 'detail'
      this.listGrid.currentData = {
        orgId: 'KAI',
        parent: null,
        status: 'ACTIVE',
        datasrc: 'EPISODE'
      }
      this.specGrid.data = []
      this.usewithGrid.data = []
    },
    addSpec: function () {
      this.specGrid.data.splice(0, 0, {
        state: 'ADDED',
        orgId: this.listGrid.currentData.orgId,
        classstructureId: this.listGrid.currentData.classstructureId,
        measureunit: 'EA'
      })
    },
    getClassspecIdChk: function (row) {
      if (this.specGrid.data.filter(data => (data.classspecId === row.classspecId)).length > 1) {
        row.classspecId = ''
        this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
      }
    },
    addUsewith: function () {
      this.usewithGrid.data.splice(0, 0, {
        state: 'ADDED',
        orgId: this.listGrid.currentData.orgId,
        classstructureId: this.listGrid.currentData.classstructureId,
        object: 'CI'
      })
    },
    openParentDialog: function () {
      this.treeParent.data = this.tree.data
      this.showParentDialogFlag = true
    },
    confirmParentDialog: function () {
      let treedata = this.$refs.treeParent.$refs.tree.getCurrentNode().data
      if (!this.$util.isNull(treedata)) {
        this.listGrid.currentData.parent = (treedata.classstructureId === 'CLASSSTRUCTURE' ? null : treedata.classstructureId)
        let orgData = this.listGrid.data.filter((listGridData, index) => {
          return (listGridData.id === this.listGrid.currentData.id)
        })
        if (this.listGrid.currentData.hasOwnProperty('id')) {
          if (treedata.hierarchypath.indexOf(orgData[0].hierarchypath) > -1) {
            this.$util.esmessage(this, 'warn', this.labels.notChildrenSelect)
            return
          }
        }
        this.showParentDialogFlag = false
        this.treeParent.data = []
        this.makeHierarchypath()
        this.changeValue(this.listGrid.currentData)
      }
    },
    cancleParentDialog: function () {
      this.showParentDialogFlag = false
      this.treeParent.data = []
      this.listGrid.currentData.parent = null
    },
    changeOrgId: function () {
      this.getClassstructureIdChk()
    },
    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },
    enterSearch: function () {
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    enterSearchSpec: function () {
      this.specGrid.currentPage = 1
      this.specGrid.filters[0].value = this.specGrid.searchParam
    },
    enterSearchUsewith: function () {
      this.usewithGrid.currentPage = 1
      this.usewithGrid.filters[0].value = this.usewithGrid.searchParam
    }
  },
  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.specGrid.data = []
    this.usewithGrid.data = []
    this.tree.rawData = []
    this.treeParent.rawData = []
    this.orgSelect = []
    this.statusSelect = []
    this.measureunitSelect = []
    this.spectypeSelect = []
    this.usewithTableSelect = []
    this.classIconSelect = []
    this.ancestor = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.treeFlag = false
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
