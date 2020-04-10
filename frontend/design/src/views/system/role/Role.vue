<!--
 File Name  : Role.vue
 Description : 롤
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.06  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
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
        <b-col sm="10">
         <div class="comm-btn-box" role="group">
            <b-button class='px-3 sm' variant="primary" v-on:click="newRole"     v-show="visibleAdd">{{labels.new}}</b-button>
            <b-button class='px-3 sm' variant="primary" v-on:click="saveRole"    v-show="visibleSave">{{labels.save}}</b-button>
            <b-button class='px-3 sm' variant="primary" v-on:click="deleteRole"  v-show="visibleDelete">{{labels.delete}}</b-button>
          </div>
        </b-col>
      </b-row>
    <el-tabs v-model="activeTab" :before-leave="chgTab">
      <!-- list -->
      <el-tab-pane  name="list" :label='labels.list'>
        <b-row>
          <div class='col-md-4'>
            <div class="search-component sm">
              <input type="text" class="form-control formdata sm" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder="labels.search" />
            </div>
          </div>
          <div class='col-md-8' >
            <div class='d-flex justify-content-end'>
              <el-button  v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0; width:40px; height: 40px;">
                <img src="../../../assets/img/icons/icon_search_download.png" style="width: 60%; "/>
              </el-button>
            </div>
          </div>
        </b-row>

        <b-row>
          <div class='col'>
            <p class='grid-line'></p>
            <data-tables id="listGrid" layout='table, pagination' :table-props="listGrid.tableProps" :page-size='10' :data='listGrid.data' :pagination-props='listGrid.pageProps' :filters='listGrid.filters'
            :current-page.sync='listGrid.pageIndex'
            v-on:filtered-data="handleFilteredData"
            v-on:row-click='selectData'
            v-if="listGridFlag" >
              <el-table-column prop="id"            :label='labels.id'            :width='listGrid.titles[0].width'></el-table-column>
              <el-table-column prop="roleId"        :label='labels.roleId'        :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column prop="description"   :label='labels.description'   ></el-table-column>
              <el-table-column prop="createDt"      :label='labels.createDt'      :width='listGrid.titles[3].width'>
                <template slot-scope="scope">
                  {{ dateTime(scope.row.createDt) }}
                </template>
              </el-table-column>
              <el-table-column prop="createNm"      :label='labels.createId'      :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column prop="updateDt"      :label='labels.updateDt'      :width='listGrid.titles[5].width'>
                <template slot-scope="scope">
                  {{ dateTime(scope.row.updateDt) }}
                </template>
              </el-table-column>
              <el-table-column prop="updateNm"      :label='labels.updateId'      :width='listGrid.titles[6].width'></el-table-column>
            </data-tables>
          </div>
        </b-row>
      </el-tab-pane>

      <!-- detail -->
      <el-tab-pane  name="detail" :label='labels.detail' v-if="(activeTab !== 'list')">
        <b-row>
          <div class='col-md-12'>
            <el-form :model="listGrid.currentData" label-width="120px" >
              <b-row>
                <div class="form-group col-md-3">
                  <label for="id">{{labels.id}}</label>
                  <input id="id" type="text" v-model="listGrid.currentData.id"  class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-3">
                  <label for="roleId">{{labels.roleId}}<span class="require-mark">*</span></label>
                  <input id="roleId" v-on:blur="blurRoldId" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.roleId" class="formdata sm float-right" type="text" :placeholder="labels.searchMessage" :disabled="listGrid.currentData.hasOwnProperty('id')">
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.roleIdMsg)" v-text="validMsg.roleIdMsg"></p>
                </div>
                <div class="form-group col-md-6">
                  <label for="role_description">{{labels.description}}<span class="require-mark">*</span></label>
                  <input id="role_description" v-on:blur="blurDescription" v-on:change="changeValue(listGrid.currentData)" v-model="listGrid.currentData.description" class="formdata sm float-right" type="text" :placeholder="labels.searchMessage">
                  <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
                </div>
              </b-row>
              <b-row>
                <div class="form-group col-md-3">
                  <label>{{labels.createId}}</label>
                  <input type="text" v-model="listGrid.currentData.createNm" placeholder="CREATER" class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-3">
                  <label>{{labels.createDt}}</label>
                  <input type="text" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-3">
                  <label>{{labels.updateId}}</label>
                  <input type="text" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" class="formdata sm float-right" disabled="disabled">
                </div>
                <div class="form-group col-md-3">
                  <label>{{labels.updateDt}}</label>
                  <input type="text" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" class="formdata sm float-right" disabled="disabled">
                </div>
              </b-row>
            </el-form>
          </div>
        </b-row>
      </el-tab-pane>

      <!-- users -->
      <el-tab-pane  name="user" :label='labels.user' v-if="(activeTab !== 'list')">

        <b-row>
          <div class="form-group col-md-3">
            <label>{{labels.id}}</label>
            <input type="text" v-model="listGrid.currentData.id"  class="formdata sm float-right" disabled="disabled">
          </div>
          <div class="form-group col-md-3">
            <label>{{labels.roleId}}<span class="require-mark">*</span></label>
            <input v-on:blur="blurRoldId" type="text" v-model="listGrid.currentData.roleId" class="formdata sm float-right" :placeholder="labels.searchMessage" :disabled="listGrid.currentData.hasOwnProperty('id')">
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.roleIdMsg)" v-text="validMsg.roleIdMsg"></p>
          </div>
          <div class="form-group col-md-6">
            <label>{{labels.description}}<span class="require-mark">*</span></label>
            <input v-on:blur="blurDescription" v-on:change="changeValue(listGrid.currentData)" type="text" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" class="formdata sm float-right" >
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
          </div>
        </b-row>
          <b-row class='mt-2'>
            <div class="col" style="border: 0.5px solid #c8d3df;"></div>
          </b-row>

        <b-row class="mt-4">
          <div class='col-md-4'>
            <div class="search-component sm">
              <input id="searchGroupUser" type="text" class="form-control formdata sm" v-model="userGrid.searchParam" v-on:keyup.enter="userSearch" :placeholder="labels.search" />
            </div>
          </div>
          <div class='col-md-6'></div>
          <div class='col-md-2'>
            <div class='d-flex justify-content-end'>
              <b-button class='px-3 sm' variant="primary"  v-on:click="openModelUser" v-show="visibleAddUser">{{labels.add}}</b-button>
            </div>
          </div>
        </b-row>

        <b-row class="mt-1">
          <div class="col">
            <p class='grid-line'></p>
            <data-tables id="userGrid" layout='table, pagination' :current-page.sync='userGrid.pageIndex' :table-props="userGrid.tableProps" :page-size='userGrid.pageProps.pageSize' :data='userGrid.data' :filters='userGrid.filters' :pagination-props='userGrid.pageProps'>
              <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column>
              <el-table-column prop="username" :label="labels.username" :sortable="true" width="180px"></el-table-column>
              <el-table-column prop="firstname" :label="labels.firstname" :sortable="true" width="180px"></el-table-column>
              <el-table-column prop="email" :label="labels.email" :sortable="true"></el-table-column>
              <el-table-column prop="mobile" :label="labels.tel" :sortable="true" width="200px"></el-table-column>
              <el-table-column prop="status" :label="labels.status" :sortable="true" width="100px"></el-table-column>
              <el-table-column align="right" width="80px">
                <template slot-scope="props">
                  <el-button  v-on:click="deleteUser(props.row)" v-show="visibleDelUser" style="padding: 0; width:30px; height: 30px;">
                    <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </b-row>
      </el-tab-pane>

      <!-- usergroup -->
      <el-tab-pane  name="usergroup" :label='labels.usergroup' v-if="(activeTab !== 'list')">
        <b-row>
          <div class="form-group col-md-3">
            <label>{{labels.id}}</label>
            <input type="text" v-model="listGrid.currentData.id"  class="formdata sm float-right" disabled="disabled">
          </div>
          <div class="form-group col-md-3">
            <label>{{labels.roleId}}<span class="require-mark">*</span></label>
            <input v-on:blur="blurRoldId" type="text" v-model="listGrid.currentData.roleId" class="formdata sm float-right" :placeholder="labels.searchMessage" :disabled="listGrid.currentData.hasOwnProperty('id')">
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.roleIdMsg)" v-text="validMsg.roleIdMsg"></p>
          </div>
          <div class="form-group col-md-6">
            <label>{{labels.description}}<span class="require-mark">*</span></label>
            <input v-on:blur="blurDescription" v-on:change="changeValue(listGrid.currentData)" type="text" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" class="formdata sm float-right" >
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
          </div>
        </b-row>
        <b-row class="mt-2">
          <div class="col" style="border: 0.5px solid #c8d3df;"></div>
        </b-row>
        <b-row class="mt-4">
          <div class='col-md-4'>
            <div class="search-component sm">
              <input id="searchGroup" type="text" class="form-control formdata sm"  v-model="groupGrid.searchParam" v-on:keyup.enter="groupSearch" :placeholder="labels.search" />
            </div>
          </div>
          <div class='col-md-6'></div>
          <div class='col-md-2'>
            <div class='d-flex justify-content-end'>
              <b-button class='px-3 sm' variant="primary" v-on:click="openModelGroup" v-show="visibleAddGroup">{{labels.add}}</b-button>
            </div>
          </div>
        </b-row>

        <b-row class="mt-1">
          <div class="col">
            <p class='grid-line'></p>
            <data-tables id="groupGrid" layout='table, pagination' :current-page.sync='groupGrid.pageIndex' :table-props="groupGrid.tableProps" :page-size='groupGrid.pageProps.pageSize' :data='groupGrid.data' :filters='groupGrid.filters' :pagination-props='groupGrid.pageProps'>
              <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column>
              <el-table-column prop="usergroupId" :label="labels.username" :sortable="true"></el-table-column>
              <el-table-column prop="userGroup.orgId" :label="labels.orgId" :sortable="true"></el-table-column>
              <el-table-column prop="userGroup.description" :label="labels.description" :sortable="true"></el-table-column>
              <el-table-column align="right" width="80">
                <template slot-scope="props">
                  <el-button  v-on:click="deleteGroup(props.row)" v-show="visibleDelGroup" style="padding: 0; width:30px; height: 30px;">
                    <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </b-row>
      </el-tab-pane>

      <!-- auth -->
      <el-tab-pane  name="auth" :label='labels.auth' v-if="(activeTab !== 'list')">
        <b-row>
          <div class="form-group col-md-3">
            <label>{{labels.id}}</label>
            <input type="text" v-model="listGrid.currentData.id"  class="formdata sm float-right" disabled="disabled">
          </div>
          <div class="form-group col-md-3">
            <label>{{labels.roleId}}<span class="require-mark">*</span></label>
            <input @blur="blurRoldId" type="text" v-model="listGrid.currentData.roleId"  class="formdata sm float-right" :placeholder="labels.searchMessage" :disabled="listGrid.currentData.hasOwnProperty('id')">
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.roleIdMsg)" v-text="validMsg.roleIdMsg"></p>
          </div>
          <div class="form-group col-md-6">
            <label>{{labels.description}}<span class="require-mark">*</span></label>
            <input @blur="blurDescription" v-on:change="changeValue(listGrid.currentData)" type="text" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" class="formdata sm float-right" >
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
          </div>
        </b-row>
        <b-row class='mt-2'>
          <div class="col" style="border: 0.5px solid #c8d3df;"></div>
        </b-row>
        <b-row class="mt-4">
          <div class='col-md-4'>
            <div class="search-component sm">
              <input id="listFilter" type="text" class="form-control formdata sm" v-model="authorityGrid.searchParam" v-on:keyup.enter="authSearch" :placeholder="labels.search" />
            </div>
          </div>
          <div class='col-md-8'>
            <div class='d-flex justify-content-end'>
              <b-button class='px-3 sm' variant="primary" v-on:click="authDeleteAll" v-show="visibleAuthDelAll">{{labels.deleteAll}}</b-button>
              <b-button class='px-3 sm' variant="primary" v-on:click="authDeleteSelected" v-show="visibleAuthDelSelected">{{labels.deleteSelected}}</b-button>
              <b-button class='px-3 sm' variant="primary" v-on:click="authAdd" v-show="visibleAuthAdd">{{labels.add}}</b-button>
            </div>
          </div>
        </b-row>

        <b-row class="mt-1">
          <div class="col">
            <p class='grid-line'></p>
            <data-tables id="authorityGrid" ref="authTable" layout='table, pagination' @selection-change="authSelectionChange" :current-page.sync='authorityGrid.pageIndex' :table-props="authorityGrid.tableProps" :data='authorityGrid.data' :filters='authorityGrid.filters' :page-size='authorityGrid.pageProps.pageSize' :pagination-props='authorityGrid.pageProps' >
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="id" :label="labels.id" width="100px;"></el-table-column>
              <el-table-column prop="roleId" :label="labels.roleId" width="200px;" >
                <template slot-scope="props">
                  <input class="formdata sm float-right" type="text" v-model="props.row.roleId" v-if="!props.row.hasOwnProperty('id')" disabled="disabled" />
                  <span v-if="props.row.hasOwnProperty('id')">{{props.row.roleId}} </span>
                </template>
              </el-table-column>
              <el-table-column  prop="appId" :label="labels.appId" width="300px;">
                <template slot-scope="props" >
                  <el-select  v-model="props.row.appId" v-if="!props.row.hasOwnProperty('id')">
                    <el-option v-for="app in appSelect"  :value="app.appId" :key="app.appId" :label="app.appId"></el-option>
                  </el-select>
                  <span v-if="props.row.hasOwnProperty('id')">{{props.row.appId}} </span>
                </template>
              </el-table-column>
              <el-table-column prop="authorityId" :label="labels.authorityId" >
                <template slot-scope="props">
                  <el-select  v-model="props.row.authorityId" v-if="!props.row.hasOwnProperty('id')">
                    <el-option v-for="auth in authSelect" :value="auth.option" :key="auth.authorityId" :optionlabel="auth.option"></el-option>
                  </el-select>
                  <span v-if="props.row.hasOwnProperty('id')">{{props.row.authorityId}} </span>
                </template>
              </el-table-column>
              <el-table-column align="right" width="80">
                <template slot-scope="props">
                  <el-button  v-on:click="deleteAuth(props.row)" v-show="visibleAuthDel" style="padding: 0; width:30px; height: 30px;">
                    <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </b-row>
      </el-tab-pane>

      <!-- filter -->
      <el-tab-pane  name="Filter" :label='labels.filter' v-if="(activeTab !== 'list')">
        <b-row>
          <div class="form-group col-md-3">
            <label>{{labels.id}}</label>
            <input type="text" v-model="listGrid.currentData.id"  class="formdata sm float-right" disabled="disabled">
          </div>
          <div class="form-group col-md-3">
            <label>{{labels.roleId}}<span class="require-mark">*</span></label>
            <input v-on:blur="blurRoldId" type="text" v-model="listGrid.currentData.roleId" class="formdata sm float-right" :placeholder="labels.searchMessage" :disabled="listGrid.currentData.hasOwnProperty('id')">
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.roleIdMsg)" v-text="validMsg.roleIdMsg"></p>
          </div>
          <div class="form-group col-md-6">
            <label>{{labels.description}}<span class="require-mark">*</span></label>
            <input @blur="blurDescription" v-on:change="changeValue(listGrid.currentData)" type="text" v-model="listGrid.currentData.description" :placeholder="labels.searchMessage" class="formdata sm float-right" >
            <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
          </div>
        </b-row>
        <b-row class='mt-2'>
          <div class="col" style="border: 0.5px solid #c8d3df;"></div>
        </b-row>
        <b-row class="mt-4">
          <div class='col-md-4'>
            <div class="search-component sm">
              <input type="text" class="form-control formdata sm" v-model="filterGrid.searchParam" v-on:keyup.enter="filterSearch" :placeholder="labels.search">
            </div>
          </div>
          <div class='col-md-8'>
            <div class='d-flex justify-content-end'>
              <b-button class='px-3 sm' variant="primary" v-on:click="filterDeleteAll" v-show="visibleFilterDelAll">{{labels.deleteAll}}</b-button>
              <b-button class='px-3 sm' variant="primary" v-on:click="filterDeleteSelected" v-show="visibleFilterSelected">{{labels.deleteSelected}}</b-button>
              <b-button class='px-3 sm' variant="primary" v-on:click="addFilter" v-show="visibleFilterAdd">{{labels.add}}</b-button>
            </div>
          </div>
        </b-row>

        <b-row class="mt-1">
          <div class="col">
            <p class='grid-line'></p>
            <data-tables id="filterGrid" ref="filterTable" layout='table, pagination' @selection-change="filterSelectionChange" :current-page.sync='filterGrid.pageIndex' :table-props='filterGrid.tableProps' :data='filterGrid.data' :filters='filterGrid.filters' :page-size='filterGrid.pageProps.pageSize' :pagination-props='filterGrid.pageProps' >
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="id" :label="labels.id" width="80"></el-table-column>
              <el-table-column prop="roleId" :label="labels.roleId" width="200px;" >
                <template slot-scope="props">
                  <input class="formdata sm float-right" type="text" v-model="props.row.roleId" v-if="!props.row.hasOwnProperty('id')" disabled="disabled" />
                  <span v-if="props.row.hasOwnProperty('id')">{{props.row.roleId}} </span>
                </template>
              </el-table-column>
              <el-table-column  prop="datafilterId" :label="labels.datafilterId" width="200" >
                <template slot-scope="props" :label="labels.datafilterId" >
                  <el-select  v-model="props.row.datafilterId" v-if="!props.row.hasOwnProperty('id')">
                    <el-option v-for="filter in filterSelect"  :value="filter" :key="filter" :label="filter"></el-option>
                  </el-select>
                  <span v-if="props.row.hasOwnProperty('id')">{{props.row.datafilterId}} </span>
                </template>
              </el-table-column>
              <el-table-column prop="whereClause" :label="labels.whereClause" >
                <template slot-scope="props">
                  <input class="form-control formdata sm" type="text" maxlength="100" v-model="props.row.whereClause" v-on:blur="saveFilter(props.row)"/>
                </template>
              </el-table-column>
              <el-table-column align="right" width="80">
                <template slot-scope="props">
                  <el-button  v-on:click="deleteFilter(props.row)" v-show="visibleFilterDel" style="padding: 0; width:30px; height: 30px;">
                    <img src="../../../assets/img/icons/icon_delete.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </div>
        </b-row>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :title="labels.userDialogTitle" :visible.sync="userDialogFlag">
      <b-row>
        <div class='col'>
          <div class="search-component sm">
            <input id="searchUser" type="text" class="formdata sm"  v-model="userSearchGrid.searchParam" v-on:keyup.enter="addUserSearch" :placeholder="labels.search" />
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="col">
          <p class='grid-line'></p>
          <data-tables layout='table, pagination' :table-props="userSearchGrid.tableProps" :current-page.sync='userSearchGrid.pageIndex' :page-size='userSearchGrid.pageProps.pageSize' :data='userSearchGrid.data' :filters='userSearchGrid.filters' :pagination-props='userSearchGrid.pageProps' @selection-change="handleUserSelectionChange">
            <el-table-column type="selection" width="60"></el-table-column>
            <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column>
            <el-table-column prop="username" :label="labels.username" :sortable="true" width="180px"></el-table-column>
            <el-table-column prop="firstname" :label="labels.firstname" :sortable="true" width="180px"></el-table-column>
            <el-table-column prop="email" :label="labels.email" :sortable="true"></el-table-column>
            <el-table-column prop="status" :label="labels.status" :sortable="true" width="100px"></el-table-column>
          </data-tables>
        </div>
      </b-row>

      <b-row class="row">
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button class='px-3 sm' variant="primary" v-on:click="addRoleUser">{{labels.add}}</b-button>
            <b-button class='px-3 sm' variant="primary" v-on:click="userDialogFlag = false">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>

    <el-dialog :title="labels.groupDialogTitle" :visible.sync="groupDialogFlag">
      <b-row>
        <div class='col'>
          <div class="search-component sm">
            <input type="text" class="formdata sm" v-model="groupSearchGrid.searchParam" v-on:keyup.enter="addGroupSearch" :placeholder="labels.search" />
          </div>
        </div>
      </b-row>

      <b-row>
        <div class="col">
          <p class='grid-line'></p>
              <data-tables-server
                ref="grid"
                :table-props='groupSearchGrid.tableProps'
                :data="groupSearchGrid.data"
                :filters="groupSearchGrid.filters"
                :total="groupSearchGrid.total"
                v-on:query-change="getUserGroup"
                @selection-change="handleGroupSelectionChange"
                :page-size='groupSearchGrid.pageProps.pageSize'
                :pagination-props="groupSearchGrid.pageProps"  :current-page.sync='groupSearchGrid.pageIndex'>
            <el-table-column type="selection" width="60"></el-table-column>
            <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column>
            <el-table-column prop="usergroupId" :label="labels.usergroupId" :sortable="true"></el-table-column>
            <el-table-column prop="orgId" :label="labels.orgId" :sortable="true"></el-table-column>
            <el-table-column prop="description" :label="labels.description" :sortable="true"></el-table-column>
              </data-tables-server>

          <!-- <data-tables layout='table, pagination' :table-props="groupSearchGrid.tableProps" :page-size='groupSearchGrid.pageProps.pageSize' :data='groupSearchGrid.data' :filters='groupSearchGrid.filters' :pagination-props='groupSearchGrid.pageProps' @selection-change="handleGroupSelectionChange">
            <el-table-column type="selection" width="60"></el-table-column>
            <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column>
            <el-table-column prop="usergroupId" :label="labels.usergroupId" :sortable="true"></el-table-column>
            <el-table-column prop="orgId" :label="labels.orgId" :sortable="true"></el-table-column>
            <el-table-column prop="description" :label="labels.description" :sortable="true"></el-table-column>
          </data-tables> -->
        </div>
      </b-row>

      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button class='px-3 sm' variant="primary" v-on:click="addRoleGroup">{{labels.add}}</b-button>
            <b-button class='px-3 sm' variant="primary" v-on:click="groupDialogFlag = false">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>

    <el-dialog :title="labels.authDialogTitle" :visible.sync="authDialogFlag">
      <b-row>
        <div class='col'>
          <div class="search-component sm">
            <input ref="authSearch" type="text" class="formdata sm" v-model="authSearchGrid.searchParam" v-on:keyup.enter="addAuthSearch" :placeholder="labels.search" />
          </div>
        </div>
      </b-row>
      <b-row>
        <div class="col">
          <p class='grid-line'></p>
          <data-tables layout='table, pagination' :current-page.sync='authSearchGrid.pageIndex' :table-props="authSearchGrid.tableProps" :page-size='authSearchGrid.pageProps.pageSize' :data='authSearchGrid.data' :filters='authSearchGrid.filters' :pagination-props='authSearchGrid.pageProps' @selection-change="handleAuthSelectionChange">
            <el-table-column type="selection" width="60"></el-table-column>
            <!-- <el-table-column prop="id" :label="labels.id" :sortable="true" width="100px"></el-table-column> -->
            <el-table-column prop="appId" :label="labels.menuId" :sortable="true"></el-table-column>
            <el-table-column prop="option" :label="labels.authorityId" :sortable="true"></el-table-column>
            <el-table-column prop="description" :label="labels.description" :sortable="true"></el-table-column>
          </data-tables>
        </div>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button class='px-3 sm' variant="primary" v-on:click="addAuthToRole">{{labels.add}}</b-button>
            <b-button class='px-3 sm' variant="primary" v-on:click="authDialogFlag = false">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
       </b-col>
      </b-row>
    </b-card>
  </div>
</template>

<script>
export default {
  name: 'ROLE',

  data () {
    return {
      labels: {
        add: '',
        title: '',
        role: '',
        user: '',
        detail: '',
        auth: '',
        filter: '',
        id: '',
        roleId: '',
        description: '',
        createDt: '',
        createId: '',
        updateDt: '',
        updateId: '',
        username: '',
        firstname: '',
        lastname: '',
        status: '',
        email: '',
        tel: '',
        mobile: '',
        search: '',
        authorityId: '',
        appId: '',
        userDialogTitle: '',
        authDialogTitle: '',
        groupDialogTitle: '',
        filterDialogTitle: '',
        datafilterId: '',
        whereClause: '',
        new: '',
        close: '',
        confirmAllAuth: '',
        confirmSelectedAuth: '',
        confirmAllFilter: '',
        confirmSelectedFilter: '',
        save: '',
        saveMessage: '',
        deleteMessage: '',
        delete: '',
        childUserMessage: '',
        childAuthMessage: '',
        childFilterMessage: '',
        roleIdMessage: '',
        descriptionMessage: '',
        roleExist: '',
        roleMessage: '',
        roleLength: '',
        descriptionLength: '',
        list: '',
        deleteAll: '',
        deleteSelected: '',
        allToUsers: '',
        checkToUsers: '',
        usergroupId: '',
        confirmDeleteAllAuth: '',
        confirmDeleteSelectedAuth: '',
        confirmDeleteAllFilter: '',
        confirmDeleteSelectedFilter: '',
        selectNullMsg: '',
        noDataMsg: '',
        orgId: '',
        childGroupMessage: '',
        excelDownload: '',
        searchMessage: '',
        filterMsg: '',
        whereClauseMsg: '',
        duplicatedMessage: '',
        chkChgDataMsg: '',
        menuId: ''
      },
      authorities: {
        ROLE_SAVE: false,
        ROLE_AUTH_SAVE: false,
        ROLE_FILTER_SAVE: false,
        ROLE_DELETE: false,
        ROLE_AUTH_DELETE: false,
        ROLE_FILTER_DELETE: false
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        rowIndex: -1,
        pageIndex: 1,
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },

        filters: [{
          prop: ['roleId', 'description'],
          value: ''
        }],
        filteredData: [],
        data: [],
        titles: [
          {prop: 'id', label: 'id', width: '100px'},
          {prop: 'roleId', label: 'roleId', width: '200px'},
          {prop: 'description', label: 'description', width: '300px'},
          {prop: 'createDt', label: 'createDt', width: '250px'},
          {prop: 'createId', label: 'createId', width: '150px'},
          {prop: 'updateDt', label: 'updateDt', width: '250px'},
          {prop: 'updateId', label: 'updateId', width: '150px'}
        ],
        columns: ['id', 'roleId', 'description', 'createDt', 'createId', 'updateDt', 'updateId'],
        columnNames: ['id', 'roleId', 'description', 'createDt', 'createId', 'updateDt', 'updateId'],
        currentData: {
        },
        searchParam: ''
      },

      authorityGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },
        filters: [{
          prop: [ 'appId', 'authorityId' ],
          value: ''
        }],
        filteredData: [],
        columns: [ 'appId', 'authorityId' ],
        columnNames: [ 'appId', 'authorityId' ],
        selection: [],
        searchParam: ''
      },

      userGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },
        filters: [{
          prop: ['username', 'firstname', 'mobile', 'email', 'status'],
          value: ''
        }],
        filteredData: [],
        columns: ['username', 'firstname', 'mobile', 'email', 'status'],
        columnNames: ['username', 'firstname', 'mobile', 'email', 'status'],
        selection: [],
        searchParam: ''
      },

      groupGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },
        filters: [{
          prop: ['usergroupId', 'orgId'],
          value: ''
        }],
        filteredData: [],
        columns: ['usergroupId', 'orgId', 'description'],
        columnNames: ['usergroupId', 'orgId', 'description'],
        selection: [],
        searchParam: ''
      },

      groupSearchGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },
        filters: [{
          prop: ['usergroupId', 'description'],
          value: ''
        }],
        filteredData: [],
        columns: ['usergroupId', 'orgId', 'description'],
        columnNames: ['usergroupId', 'orgId', 'description'],
        selection: [],
        searchParam: '',
        total: 0,
        srchCnd: null
      },

      userSearchGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },
        filters: [{
          prop: ['username', 'firstname', 'email', 'status'],
          value: ''
        }],
        filteredData: [],
        columns: ['username', 'firstname', 'email', 'status'],
        columnNames: ['username', 'firstname', 'email', 'status'],
        selection: [],
        searchParam: ''
      },

      filterGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [10, 15, 20]
        },
        filters: [{
          prop: ['roleId', 'datafilterId', 'whereClause'],
          value: ''
        }],
        filteredData: [],
        columns: ['roleId', 'datafilterId', 'whereClause'],
        columnNames: ['roleId', 'datafilterId', 'whereClause'],
        selection: [],
        searchParam: ''
      },

      authSearchGrid: {
        pageIndex: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        filters: [{
          prop: ['appId', 'option', 'description'],
          value: ''
        }],
        filteredData: [],
        columns: ['appId', 'authorityId', 'description'],
        columnNames: ['appId', 'authorityId', 'description'],
        selection: [],
        searchParam: ''
      },
      appSelect: [],
      authSelect: [],
      userSelect: [],
      filterSelect: [],
      listGridFlag: false,
      userDialogFlag: false,
      groupDialogFlag: false,
      authDialogFlag: false,
      filterDialogFlag: false,
      activeTab: 'list',
      validMsg: {
        roleIdMsg: '',
        descriptionMsg: ''
      }
    }
  },
  computed: {
    visibleDelete: function () {
      return this.authorities.ROLE_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleSave: function () {
      return this.authorities.ROLE_SAVE && (this.activeTab !== 'list')
    },
    visibleAdd: function () {
      return this.authorities.ROLE_SAVE && (this.activeTab === 'list')
    },
    visibleAddUser: function () {
      return this.authorities.ROLE_SAVE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleDelUser: function () {
      return this.authorities.ROLE_SAVE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAddGroup: function () {
      return this.authorities.ROLE_SAVE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleDelGroup: function () {
      return this.authorities.ROLE_SAVE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAuthAdd: function () {
      return this.authorities.ROLE_AUTH_SAVE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAuthDel: function () {
      return this.authorities.ROLE_AUTH_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAuthDelSelected: function () {
      return this.authorities.ROLE_AUTH_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleAuthDelAll: function () {
      return this.authorities.ROLE_AUTH_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleFilterAdd: function () {
      return this.authorities.ROLE_FILTER_SAVE && (this.activeTab !== 'list')
    },
    visibleFilterSelected: function () {
      return this.authorities.ROLE_FILTER_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleFilterDelAll: function () {
      return this.authorities.ROLE_FILTER_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleFilterDel: function () {
      return this.authorities.ROLE_FILTER_DELETE && (this.activeTab !== 'list')
    }
  },
  watch: {
    'groupDialogFlag': function (val) {
      if (val) {
        this.getUserGroup()
      }
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels, this.listGrid.titles)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      this.getRoleList()
      this.listGridFlag = true
      this.$http.get(
        '/episode/api/system/common/datafilter'
      ).then(res => {
        this.filterSelect = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    dateTime: function (date) {
      return this.$util.dateTimeWithTimeZone(date)
    },

    newRole: function () {
      this.$http.get(
        '/episode/api/system/common/datafilter'
      ).then(res => {
        this.filterSelect = res.data
      }).catch(error => {
        console.log(error)
      })
      // this.listGrid.currentData = {status: 'ACTIVE', state: 'ADDED'}
      this.listGrid.currentData = {status: 'ACTIVE'}
      this.activeTab = 'detail'
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      this.userGrid.data = []
      this.authorityGrid.data = []
      this.filterGrid.data = []
    },
    changeValue: function (data) {
      if (data.hasOwnProperty('id')) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    },
    saveRole: function () {
      let validate = false
      let roldIdChk = this.blurRoldId()
      let escriptionChk = this.blurDescription()
      let filter = this.saveFilterTest()
      if (roldIdChk && escriptionChk && filter !== false) {
        validate = true
      }
      if (validate) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/role/' + this.listGrid.currentData.id : '/episode/api/system/role'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || filter.length > 0) {
          this.listGrid.currentData.roleFilter = filter
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            this.$util.esmessage(this, 'check', this.labels.saveMessage)
            this.setData(res.data)
            this.getRoleList()
          }).catch(error => {
            console.log(error)
          })
        }
      }
    },
    deleteRole: function () {
      this.$http.get(
        '/episode/api/system/role/child/' + this.listGrid.currentData.roleId
      ).then(res => {
        if (res.data.roleuser > 0) {
          this.$util.esmessage(this, 'warn', this.labels.childUserMessage)
          return null
        }

        if (res.data.roleauth > 0) {
          this.$util.esmessage(this, 'warn', this.labels.childAuthMessage)
          return null
        }

        if (res.data.rolefilter > 0) {
          this.$util.esmessage(this, 'warn', this.labels.childFilterMessage)
          return null
        }

        if (res.data.rolegroup > 0) {
          this.$util.esmessage(this, 'warn', this.labels.childGroupMessage)
          return null
        }

        this.$http.delete(
          '/episode/api/system/role/' + this.listGrid.currentData.id
        ).then(res => {
          this.getRoleList()

          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)

          this.activeTab = 'list'
          this.listGrid.currentData = {}
        }).catch(error => {
          console.log(error)
        })
      })
    },

    authAdd: function () {
      this.authSearchGrid.filters[0].value = ''
      this.authSearchGrid.searchParam = ''
      this.authDialogFlag = true

      this.$http.get(
        '/episode/api/system/role/' + this.listGrid.currentData.id + '/optionsearch/'
      ).then(res => {
        this.authSearchGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    // get 수정건
    openModelUser: function () {
      this.userSearchGrid.filters[0].value = ''
      this.userSearchGrid.searchParam = ''
      this.userDialogFlag = true
      this.$http.get(
        '/episode/api/system/role/' + this.listGrid.currentData.id + '/usersearch'
      ).then(res => {
        this.userSearchGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    chgTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED' || (this.filterGrid.data.filter(data => (data.status === 'save')).length > 0)) {
          rtnFlag = false
          let $this = this
          this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
            function () {
              $this.saveRole()
            },
            function () {
              $this.activeTab = 'list'
              $this.listGrid.currentData = {}
              $this.filterGrid.data = []
              $this.listGrid.searchParam = ''
              $this.userGrid.searchParam = ''
              $this.groupGrid.searchParam = ''
              $this.authorityGrid.searchParam = ''
              $this.filterGrid.searchParam = ''
              $this.listGrid.filters[0].value = ''
              $this.userGrid.filters[0].value = ''
              $this.groupGrid.filters[0].value = ''
              $this.authorityGrid.filters[0].value = ''
              $this.filterGrid.filters[0].value = ''
              for (let key in $this.validMsg) {
                $this.validMsg[key] = ''
              }
            })
        } else {
          this.listGrid.searchParam = ''
          this.userGrid.searchParam = ''
          this.groupGrid.searchParam = ''
          this.authorityGrid.searchParam = ''
          this.filterGrid.searchParam = ''
          this.listGrid.filters[0].value = ''
          this.userGrid.filters[0].value = ''
          this.groupGrid.filters[0].value = ''
          this.authorityGrid.filters[0].value = ''
          this.filterGrid.filters[0].value = ''
          this.listGrid.currentData = {}
          for (let key in this.validMsg) {
            this.validMsg[key] = ''
          }
        }
      }
      return rtnFlag
    },
    handleUserSelectionChange: function (val) {
      this.userSelect = val
    },

    handleAuthSelectionChange: function (val) {
      this.authSearchGrid.selection = val
    },

    selectData: function (currentRow) {
      this.listGrid.rowIndex = this.listGrid.filteredData.indexOf(currentRow)
      this.getRowData(currentRow.id)
      this.getFilterSearchList(currentRow.id)
      this.activeTab = 'detail'
    },

    setData: function (data) {
      this.getRowData(data.id)
      this.getFilterSearchList(data.id)
    },

    getRoleList: function () {
      this.$http.get(
        '/episode/api/system/role'
      ).then(res => {
        this.listGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    getRowData: function (id) {
      this.$http.get(
        '/episode/api/system/role/' + id
      ).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get(
        '/episode/api/system/role/' + id + '/auth'
      ).then(res => {
        this.authorityGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/role/' + id + '/user/'
      ).then(res => {
        this.userGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/role/' + id + '/rolegroup'
      ).then(res => {
        this.groupGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/system/role/' + id + '/filter'
      ).then(res => {
        res.data.forEach(r => {
          r.status = ''
        })
        this.filterGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    getRoleAuth: function (id) {
      this.$http.get(
        '/episode/api/system/role/' + id + '/auth'
      ).then(res => {
        this.authorityGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    getRoleUser: function (id) {
      this.$http.get(
        '/episode/api/system/role/' + id + '/user/'
      ).then(res => {
        this.userGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    getRoleGroup: function (id) {
      this.$http.get(
        '/episode/api/system/role/' + id + '/rolegroup'
      ).then(res => {
        this.groupGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    getRoleFilter: function (id) {
      this.$http.get(
        '/episode/api/system/role/' + id + '/filter'
      ).then(res => {
        res.data.forEach(r => {
          r.status = ''
        })
        this.filterGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    getFilterSearchList: function (id) {
      this.$http.get(
        '/episode/api/system/role/' + id + '/filtersearch'
      ).then(res => {
        this.filterSelect = res.data
      }).catch(error => {
        console.log(error)
      })
    },

    addAuthToRole: function () {
      let rows = this.authSearchGrid.selection
      if (rows.length !== 0) {
        let params = {roleId: this.listGrid.currentData.roleId, rows: rows}
        this.$http.post(
          '/episode/api/system/role/auth'
          , params
        ).then(res => {
          this.getRoleAuth(this.listGrid.currentData.id)
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.authDialogFlag = false
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
      }
    },

    addRoleUser: function () {
      this.saveUser(this.userSelect)
    },

    saveUser: function (rows) {
      if (rows.length !== 0) {
        let params = {roleId: this.listGrid.currentData.roleId, rows: rows}
        this.$http.post(
          '/episode/api/system/role/user'
          , params
        ).then(res => {
          this.getRoleUser(this.listGrid.currentData.id)
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.userDialogFlag = false
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
      }
    },

    deleteUser: function (row) {
      let param = {roleId: this.listGrid.currentData.roleId, user: row, auth: this.authorityGrid.data, filter: this.filterGrid.data}
      this.$http.delete(
        '/episode/api/system/role/user/' + row.roleUserId,
        param
      ).then(res => {
        this.getRoleUser(this.listGrid.currentData.id)
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
      }).catch(error => {
        console.log(error)
      })
    },

    deleteAuth: function (row) {
      let index = this.authorityGrid.data.indexOf(row)
      if (row.state !== 'ADDED') {
        this.$http.delete(
          '/episode/api/system/role/auth/' + row.id + '/' + row.roleId
        ).then(res => {
          this.getRoleAuth(this.listGrid.currentData.id)
          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.authorityGrid.data.splice(index, 1)
      }
    },

    addFilter: function () {
      if (this.listGrid.currentData.hasOwnProperty('id')) {
        this.filterGrid.data.splice(0, 0, {roleId: this.listGrid.currentData.roleId, state: 'ADDED', status: 'save'})
      } else {
        this.filterGrid.data.splice(0, 0, {state: 'ADDED', status: 'save'})
      }
    },

    saveFilter: function (row) {
      if (row.datafilterId !== undefined && row.datafilterId !== '' && row.whereClause !== undefined && row.whereClause !== '') {
        row.status = 'save'
      }
    },

    deleteFilter: function (row) {
      let index = this.filterGrid.data.indexOf(row)
      if (row.state !== 'ADDED') {
        this.$http.delete(
          '/episode/api/system/role/filter/' + row.id
        ).then(res => {
          this.getRoleFilter(this.listGrid.currentData.id)

          this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.filterGrid.data.splice(index, 1)
      }
    },

    handleFilteredData (filteredData) {
      this.listGrid.filteredData = filteredData
    },

    exportFilter: function () {
      let lang = this.$store.getters.getUser.lang
      let fields = [
        {value: 'id', label: this.labels.id},
        {value: 'roleId', label: this.labels.roleId},
        {value: 'description', label: this.labels.description},
        {value: 'createDt', label: this.labels.createDt},
        {value: 'createId', label: this.labels.createId},
        {value: 'updateDt', label: this.labels.updateDt},
        {value: 'updateId', label: this.labels.updateId}
      ]
      let fileName = 'Role'
      if (lang === 'ko-kr') {
        fileName = this.labels.title
      }
      this.$util.csvExport(this.listGrid.filteredData, fields, this.listGrid.columnNames, fileName)
    },

    blurRoldId: function () {
      if (this.listGrid.currentData.state === 'ADDED') {
        let roleId = this.listGrid.currentData.roleId
        if (this.$util.isNull(roleId)) {
          this.validMsg.roleIdMsg = this.labels.roleMessage
          return false
        }
        if (this.$util.lengthChk(roleId.length, 4, 20)) {
          this.validMsg.roleIdMsg = this.labels.roleLength
          return false
        }
        this.$http.get('/episode/api/system/user?username=' + roleId).then(res => {
          if (res.data.length > 0) {
            this.validMsg.roleIdMsg = this.labels.roleExist
            this.listGrid.currentData.roleIdMsg = ''
          } else {
            this.validMsg.roleIdMsg = ''
          }
        }).catch(error => {
          console.log(error)
        })
        if (this.validMsg.roleIdMsg !== '') {
          return false
        } else {
          return true
        }
      } else {
        return true
      }
    },
    blurDescription: function () {
      let description = this.listGrid.currentData.description
      if (this.$util.isNull(description)) {
        this.validMsg.descriptionMsg = this.labels.descriptionMessage
        return false
      }
      if (this.$util.lengthChk(description.length, 4, 20)) {
        this.validMsg.descriptionMsg = this.labels.descriptionLength
        return false
      }
      this.validMsg.descriptionMsg = ''
      return true
    },

    authSelectionChange: function (val) {
      this.authorityGrid.selection = val
    },

    authDeleteAll: function () {
      if (this.authorityGrid.data.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noDataMsg)
        return null
      }

      let $this = this
      let params = {id: this.listGrid.currentData.id, roleId: this.listGrid.currentData.roleId, data: this.authorityGrid.data}

      this.$util.esconfirm(this, 'delete', this.labels.confirmDeleteAllAuth, function () {
        this.$http.delete(
          '/episode/api/system/role/' + params.id + '/authdeleteall'
        ).then(res => {
          $this.getRoleAuth(params.id)
          $this.$util.esmessage(this, 'delete', $this.labels.deleteMessage)
        }).catch(error => {
          console.log(error)
        })
      })
    },

    authDeleteSelected: function () {
      if (this.authorityGrid.data.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noDataMsg)
        return null
      }

      if (this.authorityGrid.selection.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return null
      }

      let $this = this
      let params = {id: this.listGrid.currentData.id, roleId: this.listGrid.currentData.roleId, data: this.authorityGrid.selection}

      this.$util.esconfirm(this, 'delete', this.labels.confirmDeleteSelectedAuth, function () {
        this.$http.post(
          '/episode/api/system/role/auth/delete', params
        ).then(res => {
          $this.getRoleAuth(params.id)
          $this.$util.esmessage(this, 'delete', $this.labels.deleteMessage)
        }).catch(error => {
          console.log(error)
        })
      })
    },

    saveFilterTest: function () {
      let data = this.filterGrid.data.filter(r => r.status === 'save')
      if (data.length === 0) {
        return []
      } else {
        let chk = true
        data.some(row => {
          if (this.$util.isNull(row.datafilterId)) {
            this.$util.esmessage(this, 'warn', this.labels.filterMsg)
            chk = false
          } else if (this.$util.isNull(row.whereClause)) {
            this.$util.esmessage(this, 'warn', this.labels.whereClauseMsg)
            chk = false
          }
          return this.$util.isNull(row.datafilterId) || this.$util.isNull(row.whereClause)
        })
        let datafilterId = ''
        for (let key in data) {
          if (datafilterId === data[key].datafilterId && !this.$util.isNull(data[key].datafilterId)) {
            this.$util.esmessage(this, 'warn', this.labels.duplicatedMessage)
            chk = false
            break
          }
          datafilterId = data[key].datafilterId
        }
        if (chk) {
          return data
        } else {
          return false
        }
      }
    },
    filterDeleteAll: function () {
      if (this.filterGrid.data.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noDataMsg)
        return null
      }
      let $this = this
      let params = {id: this.listGrid.currentData.id, roleId: this.listGrid.currentData.roleId}
      this.$util.esconfirm(this, 'delete', this.labels.confirmDeleteAllFilter, function () {
        this.$http.delete(
          '/episode/api/system/role/' + params.id + '/filterdeleteall'
        ).then(res => {
          $this.getRoleFilter(params.id)
          $this.$util.esmessage(this, 'delete', $this.labels.deleteMessage)
        }).catch(error => {
          console.log(error)
        })
      })
    },

    filterDeleteSelected: function () {
      if (this.filterGrid.data.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noDataMsg)
        return null
      }

      if (this.filterGrid.selection.length === 0) {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return null
      }

      let $this = this
      let params = {id: this.listGrid.currentData.id, roleId: this.listGrid.currentData.roleId, data: this.filterGrid.selection}
      this.$util.esconfirm(this, 'delete', this.labels.confirmDeleteSelectedFilter, function () {
        this.$http.post(
          '/episode/api/system/role/filterdelete', params
        ).then(res => {
          $this.getRoleFilter(params.id)
          $this.$util.esmessage(this, 'delete', $this.labels.deleteMessage)
        }).catch(error => {
          console.log(error)
        })
      })
    },

    filterSelectionChange: function (val) {
      this.filterGrid.selection = val
    },

    deleteGroup: function (row) {
      let param = {roleId: this.listGrid.currentData.roleId, usergroupId: row.usergroupId, auth: this.authorityGrid.data, filter: this.filterGrid.data}

      this.$http.post(
        '/episode/api/system/role/rolegroup/delete',
        param
      ).then(res => {
        this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
        this.getRoleGroup(this.listGrid.currentData.id)
      }).catch(error => {
        console.log(error)
      })
    },

    openModelGroup: function () {
      this.groupSearchGrid.filters[0].value = ''
      this.groupSearchGrid.searchParam = ''
      this.groupSearchGrid.selection = []
      this.groupDialogFlag = true
      // this.$http.post(
      //   '/episode/api/system/role/rolegroup/cd/groups',
      //   this.groupGrid.data
      // ).then(res => {
      //   this.groupSearchGrid.data = res.data
      // }).catch(error => {
      //   console.log(error)
      // })
    },
    getUserGroup: function (srchCnd) {
      this.groupSearchGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let groupSearch = this.listGrid.currentData.roleId
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        groupSearch: groupSearch}
      }
      this.$http.get(
        '/episode/api/system/usergroup',
        params
      ).then(res => {
        this.groupSearchGrid.data = res.data.data
        this.groupSearchGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },

    addRoleGroup: function () {
      let rows = this.groupSearchGrid.selection
      if (rows.length !== 0) {
        this.groupDialogFlag = false
        let params = {roleId: this.listGrid.currentData.roleId, rows: rows}
        this.$http.post(
          '/episode/api/system/role/rolegroup',
          params
        ).then(res => {
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.getRoleGroup(this.listGrid.currentData.id)
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
      }
    },
    moveChk: function (moveStr) {
      let filterData = this.filterGrid.data.filter(r => r.status === 'save')
      if (this.listGrid.currentData.state === 'CHANGED' || filterData.length > 0) {
        let $this = this
        this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
          function () {
            $this.saveRole()
          },
          function () {
            $this.userGrid.searchParam = ''
            $this.groupGrid.searchParam = ''
            $this.authorityGrid.searchParam = ''
            $this.filterGrid.searchParam = ''
            $this.userGrid.filters[0].value = ''
            $this.groupGrid.filters[0].value = ''
            $this.authorityGrid.filters[0].value = ''
            $this.filterGrid.filters[0].value = ''
            $this.movePage(moveStr)
          })
      } else {
        this.userGrid.searchParam = ''
        this.groupGrid.searchParam = ''
        this.authorityGrid.searchParam = ''
        this.filterGrid.searchParam = ''
        this.userGrid.filters[0].value = ''
        this.groupGrid.filters[0].value = ''
        this.authorityGrid.filters[0].value = ''
        this.filterGrid.filters[0].value = ''
        this.movePage(moveStr)
      }
    },
    movePage: function (moveStr) {
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      let oldRowIndex = this.listGrid.rowIndex
      let oldPageIndex = this.listGrid.pageIndex
      if (moveStr === 'previous') {
        this.listGrid.rowIndex = this.listGrid.rowIndex - 1
      } else {
        this.listGrid.rowIndex = this.listGrid.rowIndex + 1
      }
      let gridRowIndex = this.listGrid.rowIndex - (this.listGrid.pageProps.pageSize * (this.listGrid.pageIndex - 1))
      if (gridRowIndex < 0) {
        this.listGrid.pageIndex = this.listGrid.pageIndex - 1
      } else if (this.listGrid.pageProps.pageSize - 1 < gridRowIndex) {
        this.listGrid.pageIndex = this.listGrid.pageIndex + 1
      }
      let data = this.listGrid.filteredData
      if (this.listGrid.pageIndex === 0 || (data.length - 1 < this.listGrid.rowIndex)) {
        this.listGrid.rowIndex = oldRowIndex
        this.listGrid.pageIndex = oldPageIndex
      }
      this.getRowData(data[this.listGrid.rowIndex].id)
    },
    handleGroupSelectionChange: function (val) {
      this.groupSearchGrid.selection = val
    },
    enterSearch: function () {
      this.listGrid.pageIndex = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
    },
    authSearch: function () {
      this.listGrid.pageIndex = 1
      this.authorityGrid.filters[0].value = this.authorityGrid.searchParam
    },
    userSearch: function () {
      this.listGrid.pageIndex = 1
      this.userGrid.filters[0].value = this.userGrid.searchParam
    },
    groupSearch: function () {
      this.groupGrid.pageIndex = 1
      this.groupGrid.filters[0].value = this.groupGrid.searchParam
    },
    filterSearch: function () {
      this.filterGrid.pageIndex = 1
      this.filterGrid.filters[0].value = this.filterGrid.searchParam
    },
    addAuthSearch: function () {
      this.authSearchGrid.pageIndex = 1
      this.authSearchGrid.filters[0].value = this.authSearchGrid.searchParam
    },
    addUserSearch: function () {
      this.userSearchGrid.pageIndex = 1
      this.userSearchGrid.filters[0].value = this.userSearchGrid.searchParam
    },
    addGroupSearch: function () {
      this.groupSearchGrid.pageIndex = 1
      this.groupSearchGrid.filters[0].value = this.groupSearchGrid.searchParam
    }
  },

  created: function () {
    this.initialize()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.authorityGrid.data = []
    this.userGrid.data = []
    this.groupGrid.data = []
    this.groupSearchGrid.data = []
    this.userSearchGrid.data = []
    this.filterGrid.data = []
    this.authSearchGrid.data = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}
</script>
