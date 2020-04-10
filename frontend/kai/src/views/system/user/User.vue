<!--
 File Name  : User.vue
 Description : 유저
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
            <b-col sm='10'>
              <div class="comm-btn-box" role="group">
                <b-button variant="primary" class="px-3 sm" v-show="visibleDelete"   v-on:click="del">{{labels.delete}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleSave"     v-on:click="save">{{labels.save}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visiblePwChange" v-on:click="openDialog">{{labels.pwChange}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-show="visibleAdd"      v-on:click="add">{{labels.new}}</b-button>
              </div>
            </b-col>
          </b-row>

      <el-tabs v-model="activeTab" :before-leave="chgTab">
        <!-- list -->
        <el-tab-pane :label="labels.list" name="list">
          <b-row>
            <div class='col-md-2'>
              <div class="search-component sm">
                <input class="form-control formdata sm" type="text" v-model="listGrid.searchParam" v-on:keyup.enter="enterSearch" :placeholder="labels.username" />
              </div>
            </div>
            <div class='col-md-2'>
              <div class="search-component sm">
                <input class="form-control formdata sm" type="text" v-model="listGrid.searchName" v-on:keyup.enter="enterSearch" :placeholder="labels.firstname" />
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
              <data-tables-server
                v-if="listGridFlag"
                ref="grid"
                :table-props='listGrid.tableProps'
                :data="listGrid.data"
                :filters="listGrid.filters"
                :total="listGrid.total"
                v-on:query-change="getListData"
                v-on:row-click="handleRowClick"
                :page-size='listGrid.pageProps.pageSize'
                :pagination-props="listGrid.pageProps"  :current-page.sync='listGrid.currentPage'>
                <el-table-column prop="id"        :label="labels.id"        :sortable="true" :width='listGrid.titles[0].width'></el-table-column>
                <el-table-column prop="orgId"     :label="labels.orgId"     :sortable="true" :width='listGrid.titles[1].width'></el-table-column>
                <el-table-column prop="username"  :label="labels.username"  :sortable="true"                                  ></el-table-column>
                <el-table-column prop="firstname" :label="labels.firstname" :sortable="true" :width='listGrid.titles[3].width'></el-table-column>
                <el-table-column prop="status"    :label="labels.status"    :sortable="true" :width='listGrid.titles[4].width'></el-table-column>
                <el-table-column prop="createDt"  :label="labels.createDt"  :sortable="true" :width='listGrid.titles[5].width'>
                  <template slot-scope="scope">{{dateTime(scope.row.createDt)}}</template>
                </el-table-column>
                <el-table-column prop="createNm"  :label="labels.createId"  :sortable="true" :width='listGrid.titles[6].width'></el-table-column>
              </data-tables-server>
            </div>
          </b-row>
        </el-tab-pane>

        <!-- detail -->
        <el-tab-pane :label="labels.detail" name="detail" v-if="(activeTab !== 'list')">
          <b-row>
            <div class='col'>
              <el-form :model="listGrid.currentData" ref="userForm" label-width="120px" >
                <div class="form-row mt-2">
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="username" >{{labels.username}}<span class="require-mark">*</span></label>
                    <input type="text" id="username" class="form-control formdata sm" v-model="listGrid.currentData.username" v-on:blur="blurUsername" v-on:change="changeValue(listGrid.currentData)" :disabled="listGrid.currentData.hasOwnProperty('id')" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.username)" v-text="validMsg.username"></p>
                  </div>
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="firstname">{{labels.firstname}}<span class="require-mark">*</span></label>
                    <input type="text" id="firstname" class="form-control formdata sm" v-model="listGrid.currentData.firstname" v-on:blur="blurFirstname" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.firstname)" v-text="validMsg.firstname"></p>
                  </div>
                    <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="mobile">{{labels.mobile}}</label>
                    <input type="text" id="mobile" class="form-control formdata sm" v-model="listGrid.currentData.mobile" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData" maxlength="13"/>
                  </div>
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="email" >{{labels.email}}<span class="require-mark">*</span></label>
                    <input type="email" id="email" class="form-control formdata sm" v-model="listGrid.currentData.email" v-on:blur="blurEmail" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.email)" v-text="validMsg.email"></p>
                  </div>
                </div>
                <div class="form-row mt-2">
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label>{{labels.orgId}}<span class="require-mark">*</span></label>
                    <el-select v-model="listGrid.currentData.orgId" style="width:100%" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
                      <el-option v-for="org in orgSelect" :prop="org.orgId" :value="org.orgId" :key="org.orgId">
                        {{org.orgId}}
                      </el-option>
                    </el-select>
                  </div>
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label>{{labels.status}}<span class="require-mark">*</span></label>
                    <el-select id="status" v-model="listGrid.currentData.status" style="width:100%" v-on:change="changeValue(listGrid.currentData)">
                      <el-option v-for="status in userStatusSelect" :prop="status.value" :value="status.value" :key="status.key">
                        {{ status.value }}
                      </el-option>
                    </el-select>
                  </div>
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label>{{labels.lang}}<span class="require-mark">*</span></label>
                    <el-select id="lang" v-model="listGrid.currentData.lang" style="width:100%" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
                      <el-option v-for="item in langSelect" :prop="item.value" :value="item.value" :key="item.key">
                        {{ item.value }}
                      </el-option>
                    </el-select>
                  </div>
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="userGroup" >{{labels.userGroup}}</label>
                    <input type="test" id="userGroup" class="form-control formdata sm" v-model="listGrid.currentData.defaultGroup" v-on:change="changeValue(listGrid.currentData)"/>
                  </div>
                </div>
                <div class="form-row mt-2">
                 <div class="form-group col-md-12" style="padding-right:1%">
                    <label for="address" >{{labels.address}}</label>
                    <input type="text" id="address" class="form-control formdata sm" v-model="listGrid.currentData.address" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData" />
                  </div>
                </div>

                <div class="form-row mt-2">
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="createId">{{labels.createId}}</label>
                    <input id="createId" class="form-control formdata sm" v-model="listGrid.currentData.createNm" placeholder="CREATER" disabled="disabled" />
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="createDt">{{labels.createDt}}</label>
                    <input id="createDt" class="form-control formdata sm" :value="dateTime(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled"/>
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="updateId">{{labels.updateId}}</label>
                    <input id="updateId" class="form-control formdata sm" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled" />
                  </div>
                  <div class='form-group col-md-3' style="padding-right:1%">
                    <label for="updateDt">{{labels.updateDt}}</label>
                    <input id="updateDt" class="form-control formdata sm" :value="dateTime(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
                  </div>
                </div>
              </el-form>
            </div>
          </b-row>
        </el-tab-pane>
        <!-- role -->
        <el-tab-pane :label="labels.role" name="role" v-if="activeTab !== 'list' && listGrid.currentData.state !== 'ADDED'">
          <b-row>
            <div class='col'>
              <el-form :model="listGrid.currentData" label-width="120px" >
                <div class="form-row mt-2">
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="username" >{{labels.username}}<span class="require-mark">*</span></label>
                    <input type="text" id="username" class="form-control formdata sm" v-model="listGrid.currentData.username" :disabled="listGrid.currentData.hasOwnProperty('id')" :placeholder="labels.searchData"/>
                  </div>

                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="firstname">{{labels.firstname}}<span class="require-mark">*</span></label>
                    <input type="text" id="firstname" class="form-control formdata sm" v-model="listGrid.currentData.firstname" v-on:blur="blurFirstname" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.firstname)" v-text="validMsg.firstname"></p>
                  </div>
                    <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="mobile" >{{labels.mobile}}</label>
                    <input type="text" id="mobile" class="form-control formdata sm" v-model="listGrid.currentData.mobile" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData" maxlength="13"/>
                  </div>

                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="email" >{{labels.email}}<span class="require-mark">*</span></label>
                    <input type="email" id="email" class="form-control formdata sm" v-model="listGrid.currentData.email" v-on:blur="blurEmail"  v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.email)" v-text="validMsg.email"></p>
                  </div>
                </div>
              </el-form>
            </div>
          </b-row>

          <b-row class='mt-2'>
            <div class="col" style="border: 0.5px solid #c8d3df;"></div>
          </b-row>
          <b-row class="mt-4">
            <div class='col-md-4'>
              <input type="text" class="form-control formdata sm" v-model="roleGrid.searchParam" v-on:keyup.enter="roleSearch" :placeholder="labels.search" />
            </div>
            <div class='col-md-8' >
              <div class='d-flex justify-content-end'>
              </div>
            </div>
          </b-row>

          <b-row class="mt-1">
            <div class="col">
              <p class='grid-line'></p>
              <data-tables ref="roleTable" layout='table, pagination' :current-page.sync='roleGrid.currentPage' :table-props='roleGrid.tableProps' :data='roleGrid.data' :filters='roleGrid.filters' :page-size='roleGrid.pageProps.pageSize' :pagination-props='roleGrid.pageProps' >
                <el-table-column prop="roleId" :label="labels.roleId"></el-table-column>
                <el-table-column prop="description" :label="labels.description" ></el-table-column>
              </data-tables>
            </div>
          </b-row>
        </el-tab-pane>
        <!-- authorization -->
        <el-tab-pane :label="labels.authorization" name="auth" v-if="activeTab !== 'list' && listGrid.currentData.state !== 'ADDED'">
          <b-row>
            <div class='col'>
              <el-form :model="listGrid.currentData" label-width="120px" >
                <div class="form-row mt-2">
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="username" >{{labels.username}}<span class="require-mark">*</span></label>
                    <input type="text" id="username" class="form-control formdata sm" v-model="listGrid.currentData.username" :disabled="listGrid.currentData.hasOwnProperty('id')" :placeholder="labels.searchData"/>
                  </div>

                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="firstname">{{labels.firstname}}<span class="require-mark">*</span></label>
                    <input type="text" id="firstname" class="form-control formdata sm" v-model="listGrid.currentData.firstname" v-on:blur="blurFirstname" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.firstname)" v-text="validMsg.firstname"></p>
                  </div>
                    <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="mobile" >{{labels.mobile}}</label>
                    <input type="text" id="mobile" class="form-control formdata sm" v-model="listGrid.currentData.mobile" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData" maxlength="13"/>
                  </div>

                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="email" >{{labels.email}}<span class="require-mark">*</span></label>
                    <input type="email" id="email" class="form-control formdata sm" v-model="listGrid.currentData.email" v-on:blur="blurEmail"  v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.email)" v-text="validMsg.email"></p>
                  </div>
                </div>
              </el-form>
            </div>
          </b-row>

          <b-row class='mt-2'>
            <div class="col" style="border: 0.5px solid #c8d3df;"></div>
          </b-row>
          <b-row class="mt-4">
            <div class='col-md-4'>
              <input type="text" class="form-control formdata sm" v-model="authorityGrid.searchParam" v-on:keyup.enter="authSearch" :placeholder="labels.search" />
            </div>
            <div class='col-md-8' >
              <div class='d-flex justify-content-end'>
              </div>
            </div>
          </b-row>

          <b-row class="mt-1">
            <div class="col">
              <p class='grid-line'></p>
              <data-tables ref="authTable" layout='table, pagination' :current-page.sync='authorityGrid.currentPage' :table-props='authorityGrid.tableProps' :data='authorityGrid.data' :filters='authorityGrid.filters' :page-size='authorityGrid.pageProps.pageSize' :pagination-props='authorityGrid.pageProps' >
                <el-table-column prop="appId" :label="labels.appId"></el-table-column>
                <el-table-column prop="authorityId" :label="labels.authorityId" ></el-table-column>
              </data-tables>
            </div>
          </b-row>
        </el-tab-pane>

        <!-- datafilter -->
        <el-tab-pane :label="labels.datafilter" name="datafilter" v-if="(activeTab !== 'list') && listGrid.currentData.state !== 'ADDED'">
          <b-row>
            <div class='col'>
              <el-form :model="listGrid.currentData" label-width="120px" >
                <div class="form-row mt-2">
                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="username" >{{labels.username}}<span class="require-mark">*</span></label>
                    <input type="text" id="username" class="form-control formdata sm" v-model="listGrid.currentData.username" :disabled="listGrid.currentData.hasOwnProperty('id')" :placeholder="labels.searchData"/>
                  </div>

                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="firstname">{{labels.firstname}}<span class="require-mark">*</span></label>
                    <input type="text" id="firstname" class="form-control formdata sm" v-model="listGrid.currentData.firstname" v-on:blur="blurFirstname" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.firstname)" v-text="validMsg.firstname"></p>
                  </div>
                    <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="mobile" >{{labels.mobile}}</label>
                    <input type="text" id="mobile" class="form-control formdata sm" v-model="listGrid.currentData.mobile" v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData" maxlength="13"/>
                  </div>

                  <div class="form-group col-md-3" style="padding-right:1%">
                    <label for="email" >{{labels.email}}<span class="require-mark">*</span></label>
                    <input type="email" id="email" class="form-control formdata sm" v-model="listGrid.currentData.email" v-on:blur="blurEmail"  v-on:change="changeValue(listGrid.currentData)" :placeholder="labels.searchData"/>
                    <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.email)" v-text="validMsg.email"></p>
                  </div>
                </div>
              </el-form>
            </div>
          </b-row>

          <b-row class='mt-2'>
            <div class="col" style="border: 0.5px solid #c8d3df;"></div>
          </b-row>

          <b-row class="mt-4">
            <div class='col-md-4'>
              <input type="text" class="form-control formdata sm" v-model="filterGrid.searchParam" v-on:keyup.enter="filterSearch" :placeholder="labels.search" />
            </div>
            <div class='col-md-8' >
              <div class='d-flex justify-content-end'>
              </div>
            </div>
          </b-row>

          <b-row class="mt-1">
            <div class="col">
              <p class='grid-line'></p>
              <data-tables ref="filterTable" layout='table, pagination' :current-page.sync='filterGrid.currentPage' :table-props='filterGrid.tableProps' :data='filterGrid.data' :filters='filterGrid.filters' :page-size='filterGrid.pageProps.pageSize' :pagination-props='filterGrid.pageProps' >
                <el-table-column  prop="datafilterId" :label="labels.datafilterId" width="300" ></el-table-column>
                <el-table-column prop="whereClause" :label="labels.whereClause" ></el-table-column>
              </data-tables>
            </div>
          </b-row>
        </el-tab-pane>
      </el-tabs>
      <!-- password change Dialog -->
      <el-dialog :title="labels.title" :visible.sync="dialogFormVisible">
        <el-form :model="listGrid.currentData" ref="orgForm" label-width="120px">
          <b-row class="mt-2">
            <div class="col-md-4">
              <label>{{labels.id}}</label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="listGrid.currentData.id" autocomplete="off" disabled="disabled"/>
            </div>
          </b-row>
          <b-row class="mt-2">
            <div class="col-md-4">
              <label>{{labels.username}}</label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="listGrid.currentData.username" autocomplete="off" disabled="disabled"/>
            </div>
          </b-row>
          <b-row class="mt-2">
            <div class="col-md-4">
              <label>{{labels.newPassword}}<span class="require-mark">*</span></label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="passwordChange.newPw" autocomplete="off" type='password' v-on:blur="blurPw" maxlength="20"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.changePw)" v-text="validMsg.changePw"></p>
            </div>
          </b-row>
          <b-row class="mt-2">
            <div class="col-md-4">
              <label>{{labels.confirmNewPassword}}<span class="require-mark">*</span></label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="passwordChange.confirmNewPw" autocomplete="off" type='password' v-on:change="confirmPw" maxlength="20"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.confrimPw)" v-text="validMsg.confrimPw"></p>
            </div>
          </b-row>
          <b-row class="mt-2">
            <div class="col">
              <b-button class='px-3 sm' variant="primary" v-on:click="pwSave">{{labels.save}}</b-button>
              <b-button class='px-3 sm' variant="primary" v-on:click="close">{{labels.close}}</b-button>
            </div>
          </b-row>
        </el-form>
      </el-dialog>

     </b-col>
    </b-row>
    </b-card>
    </div>
</template>

<script>
export default {
  name: 'USER',
  data () {
    return {
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
        firstname: '',
        lastname: '',
        status: '',
        email: '',
        mobile: '',
        description: '',
        lang: '',
        authorization: '',
        authorityId: '',
        appId: '',
        datafilterId: '',
        whereClause: '',
        searchData: '',
        selectData: '',
        usernameMessage: '',
        saveMessage: '',
        failMessage: '',
        userfitlerMessage: '',
        authMessage: '',
        deleteMessage: '',
        newPassword: '',
        confirmNewPassword: '',
        lengthMessage: '',
        pwlengthMessage: '',
        pwConfirm: '',
        pwChange: '',
        useridMessage: '',
        search: '',
        excelDownload: '',
        emailMessage: '',
        deleteall: '',
        deleteselected: '',
        deleteAll: '',
        deleteSelect: '',
        mobileTypeMsg: '',
        firstnameMessage: '',
        lastnameMessage: '',
        emailMessageA: '',
        telMessage: '',
        multipleAdd: '',
        authDialogTitle: '',
        add: '',
        createId: '',
        createDt: '',
        updateId: '',
        updateDt: '',
        address: '',
        option: '',
        deleteSuccess: '',
        pwMsg: '',
        selectNullMsg: '',
        userGroup: '',
        chkChgDataMsg: '',
        role: '',
        roleId: ''
      },
      isProcess: false,
      activeTab: 'list',
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
        },
        currentPage: 1,
        rowIndex: -1,
        filters: [{
          prop: ['username'],
          value: ''
        }, {
          prop: ['firstname'],
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
        searchParam: '',
        searchName: '',
        total: 0,
        srchCnd: null
      },
      roleGrid: {
        currentPage: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [5, 10]
        },
        filters: [{
          prop: [ 'roleId', 'description' ],
          value: ''
        }],
        filteredData: [],
        columns: [ 'roleId', 'description' ],
        columnNames: [ 'roleId', 'description' ],
        searchParam: ''
      },
      authorityGrid: {
        currentPage: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [5, 10]
        },
        filters: [{
          prop: [ 'authorityId' ],
          value: ''
        }],
        filteredData: [],
        columns: [ 'appId', 'authorityId' ],
        columnNames: [ 'appId', 'authorityId' ],
        searchParam: ''
      },
      filterGrid: {
        currentPage: 1,
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return ' font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          },
          cekkStyle: ''
        },
        data: [],
        currentData: {
        },
        pageProps: {
          pageSize: 10,
          pageSizes: [5, 10]
        },
        filters: [{
          prop: ['datafilterId'],
          value: ''
        }],
        filteredData: [],
        columns: ['datafilterId'],
        columnNames: ['datafilterId'],
        searchParam: ''
      },
      authSearchGrid: {
        currentPage: 1,
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
        columns: ['appId', 'option', 'description'],
        columnNames: ['appId', 'option', 'description'],
        selection: []
      },
      passwordChange: {
        newPw: '',
        confirmNewPw: ''
      },
      userStatusSelect: [],
      orgSelect: [],
      langSelect: [],
      dialogFormVisible: false,
      listGridFlag: false,
      email: '',
      authDialogFlag: false,
      authorities: {
        USER_SAVE: false,
        USER_DELETE: false
      },
      validMsg: {
        username: '',
        firstname: '',
        email: '',
        changePw: '',
        confrimPw: ''
      }
    }
  },
  computed: {
    visibleDelete: function () {
      return this.authorities.USER_DELETE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    },
    visibleSave: function () {
      return this.authorities.USER_SAVE && (this.activeTab !== 'list')
    },
    visibleAdd: function () {
      return this.authorities.USER_SAVE && (this.activeTab === 'list')
    },
    visiblePwChange: function () {
      return this.authorities.USER_SAVE && (this.activeTab !== 'list') && this.listGrid.currentData.hasOwnProperty('id')
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      let authorities = this.$store.getters.getAuth
      authorities.forEach(authority => {
        this.authorities[authority.authorityId] = true
      })
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'userStatusSelect', domainId: 'USERSTATUS'},
          {dataObj: 'langSelect', domainId: 'LANG'}
        ]
      }
      this.$util.getComboData(param)
      this.listGridFlag = true
      this.$http.get('/episode/api/system/common/organization').then(res => {
        this.orgSelect = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let searchName = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        searchName = srchCnd.filters[1].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        searchName: searchName}
      }
      this.$http.get(
        '/episode/api/system/user',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    blurPw: function () {
      var pw = this.passwordChange.newPw
      var num = pw.search(/[0-9]/g)
      var eng = pw.search(/[a-z]/ig)
      var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi)
      if (num < 0 || eng < 0 || spe < 0) {
        this.validMsg.changePw = this.labels.pwMsg
        return false
      } else if (this.$util.lengthChk(pw.length, 8, 20)) {
        this.validMsg.changePw = this.labels.pwlengthMessage
        return false
      } else {
        this.validMsg.changePw = ''
        return true
      }
    },
    confirmPw: function () {
      if (this.passwordChange.newPw !== this.passwordChange.confirmNewPw) {
        this.validMsg.confrimPw = this.labels.pwConfirm
        return false
      } else {
        this.validMsg.confrimPw = ''
        return true
      }
    },
    changeValue: function (data) {
      if (data.hasOwnProperty('id')) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    },
    blurUsername: function () {
      if (this.listGrid.currentData.state === 'ADDED') {
        let username = this.listGrid.currentData.username
        if (this.$util.isNull(username)) {
          this.validMsg.username = this.labels.useridMessage
          return false
        }
        if (this.$util.lengthChk(username.length, 4, 20)) {
          this.validMsg.username = this.labels.lengthMessage
          return false
        }
        this.$http.get('/episode/api/system/user?username=' + username).then(res => {
          if (res.data.length > 0) {
            this.validMsg.username = this.labels.usernameMessage
            this.listGrid.currentData.username = ''
          } else {
            this.validMsg.username = ''
          }
        }).catch(error => {
          console.log(error)
        })
        if (this.validMsg.username !== '') {
          return false
        } else {
          return true
        }
      } else {
        return true
      }
    },
    blurFirstname: function () {
      if (this.$util.isNull(this.listGrid.currentData.firstname)) {
        this.validMsg.firstname = this.labels.firstnameMessage
        return false
      }
      this.validMsg.firstname = ''
      return true
    },
    blurEmail: function () {
      var exptext = /^[A-Za-z0-9_/./-]+@[A-Za-z0-9/-]+\.[A-Za-z0-9/-]+/
      var email = this.listGrid.currentData.email
      if (this.$util.isNull(email)) {
        this.validMsg.email = this.labels.emailMessageA
        return false
      }
      if (!exptext.test(email)) {
        this.validMsg.email = this.labels.emailMessage
        return false
      }
      this.validMsg.email = ''
      return true
    },
    // password ( dialog open / close / save)
    openDialog: function () {
      this.dialogFormVisible = true
      this.passwordChange.confirmNewPw = ''
      this.passwordChange.newPw = ''
      this.validMsg.changePw = ''
      this.validMsg.confrimPw = ''
    },
    close: function () {
      this.dialogFormVisible = false
    },
    pwSave: function () {
      let validate = false
      let pwChk = this.blurPw()
      let confirmPwChk = this.confirmPw()
      if (pwChk && confirmPwChk) {
        validate = true
      }
      this.listGrid.currentData.password = this.passwordChange.confirmNewPw
      if (validate) {
        this.$http.put(
          '/episode/api/system/user/pw/' + this.listGrid.currentData.id, this.listGrid.currentData
        ).then(res => {
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.dialogFormVisible = false
        }).catch(error => {
          this.$util.esmessage(this, 'warn', this.labels.failMessage)
          console.log(error)
        })
      }
    },
    chgTab: function (activeTab, oldTab) {
      let rtnFlag = true
      if (activeTab === 'list') {
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
              $this.authorityGrid.searchParam = ''
              $this.filterGrid.searchParam = ''
              $this.roleGrid.searchParam = ''
              $this.filterGrid.filters[0].value = ''
              $this.authorityGrid.filters[0].value = ''
              $this.roleGrid.filters[0].value = ''
              for (let key in $this.validMsg) {
                $this.validMsg[key] = ''
              }
            })
        } else {
          this.authorityGrid.searchParam = ''
          this.filterGrid.searchParam = ''
          this.roleGrid.searchParam = ''
          this.filterGrid.filters[0].value = ''
          this.authorityGrid.filters[0].value = ''
          this.roleGrid.filters[0].value = ''
          this.listGrid.currentData = {}
          for (let key in this.validMsg) {
            this.validMsg[key] = ''
          }
        }
      }
      return rtnFlag
    },

    // User ( add / save / delete )
    add: function () {
      // this.listGrid.currentData = {status: 'ACTIVE', password: '123456', state: 'ADDED', orgId: this.orgSelect[0].orgId, lang: this.langSelect[1].value, changepassword: true}
      this.listGrid.currentData = {status: 'ACTIVE', password: '123456', orgId: this.orgSelect[0].orgId, lang: this.langSelect[1].value, changepassword: true}
      this.authorityGrid.data = []
      this.filterGrid.data = []
      this.activeTab = 'detail'
      this.validMsg.username = ''
      this.validMsg.firstname = ''
      this.validMsg.email = ''
    },
    isValidationCheck: function () {
      let usernameChk = this.blurUsername()
      let firstnameChk = this.blurFirstname()
      let emailChk = this.blurEmail()
      if (usernameChk && firstnameChk && emailChk) {
        return true
      }
    },
    save: function () {
      if (this.isValidationCheck()) {
        let method = this.listGrid.currentData.hasOwnProperty('id') ? 'PUT' : 'POST'
        let url = this.listGrid.currentData.hasOwnProperty('id') ? '/episode/api/system/user/' + this.listGrid.currentData.id : '/episode/api/system/user'
        if (this.listGrid.currentData.state === 'CHANGED' || this.listGrid.currentData.state === 'ADDED') {
          this.$http({
            method: method,
            url: url,
            data: this.listGrid.currentData
          }).then(res => {
            if (method === 'PUT') {
              this.getRowData(this.listGrid.currentData.id)
              this.getListData()
              this.$util.esmessage(this, 'check', this.labels.saveMessage)
            } else {
              this.listGrid.currentData = res.data
              this.listGrid.data.push(res.data)
              this.getRowData(res.data.id)
              this.getListData()
              this.$util.esmessage(this, 'check', this.labels.saveMessage)
            }
          }).catch(error => {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
            console.log(error)
          })
        }
      }
    },
    del: function () {
      if (this.listGrid.currentData.hasOwnProperty('id')) {
        let id = this.listGrid.currentData.id
        let username = this.listGrid.currentData.username
        if (this.filterGrid.data.filter(filter => filter.username === username).length > 0) {
          this.$util.esmessage(this, 'warn', this.labels.userfitlerMessage)
          return
        }
        if (this.authorityGrid.data.filter(auth => auth.username === username).length > 0) {
          this.$util.esmessage(this, 'warn', this.labels.authMessage)
          return
        }
        this.$http.delete('/episode/api/system/user/' + id).then(res => {
          let index = this.listGrid.data.indexOf(this.listGrid.data.filter(user => user.id === id)[0])
          this.listGrid.data.splice(index, 1)
        }).catch(error => {
          console.log(error)
        })
      }
      this.listGrid.currentData = {}
      this.activeTab = 'list'
      this.$util.esmessage(this, 'delete', this.labels.deleteMessage)
    },
    // excel download
    exportFilter: function () {
      let searchParam = this.listGrid.filters[0].value
      let searchName = this.listGrid.filters[1].value
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'id'
      let sort = 'ascending'
      let lang = this.$store.getters.getUser.lang
      if (srchCnd != null && srchCnd !== undefined && srchCnd !== 'undefined') {
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
      }
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        searchName: searchName}
      }
      this.$http.get(
        '/episode/api/system/user',
        params
      ).then(res => {
        let fileName = 'User'
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
        this.$util.csvExport(res.data.data, fields, this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    // ++
    handleRowClick: function (row, column, event) {
      this.validMsg.username = ''
      this.validMsg.firstname = ''
      this.validMsg.email = ''
      this.activeTab = 'detail'
      this.listGrid.rowIndex = this.listGrid.data.indexOf(row)
      this.getRowData(row.id)
    },
    getRowData: function (id) {
      this.$http.get('/episode/api/system/user/' + id).then(res => {
        this.listGrid.currentData = res.data
      }).catch(error => {
        console.log(error)
      })
      this.$http.get('/episode/api/system/user/' + id + '/role').then(res => {
        this.roleGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get('/episode/api/system/user/' + id + '/userauth').then(res => {
        this.authorityGrid.data = res.data
      }).catch(error => {
        console.log(error)
      })

      this.$http.get('/episode/api/system/user/' + id + '/userfilter').then(res => {
        this.filterGrid.data = res.data
      }).catch(error => {
        console.log(error)
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
      this.listGrid.currentPage = 1
      this.listGrid.filters[0].value = this.listGrid.searchParam
      this.listGrid.filters[1].value = this.listGrid.searchName
    },
    roleSearch: function () {
      this.roleGrid.currentPage = 1
      this.roleGrid.filters[0].value = this.roleGrid.searchParam
    },
    authSearch: function () {
      this.authorityGrid.currentPage = 1
      this.authorityGrid.filters[0].value = this.authorityGrid.searchParam
    },
    filterSearch: function () {
      this.filterGrid.currentPage = 1
      this.filterGrid.filters[0].value = this.filterGrid.searchParam
    },
    moveChk: function (moveStr) {
      if (this.listGrid.currentData.state === 'CHANGED') {
        let $this = this
        this.$util.esconfirm(this, 'check', this.labels.chkChgDataMsg,
          function () {
            $this.save()
          },
          function () {
            $this.authorityGrid.searchParam = ''
            $this.filterGrid.searchParam = ''
            $this.roleGrid.searchParam = ''
            $this.filterGrid.filters[0].value = ''
            $this.authorityGrid.filters[0].value = ''
            $this.roleGrid.filters[0].value = ''
            $this.movePage(moveStr)
          })
      } else {
        this.authorityGrid.searchParam = ''
        this.filterGrid.searchParam = ''
        this.roleGrid.searchParam = ''
        this.filterGrid.filters[0].value = ''
        this.authorityGrid.filters[0].value = ''
        this.roleGrid.filters[0].value = ''
        this.movePage(moveStr)
      }
    },
    movePage: function (moveStr) {
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      let srchCnd = this.listGrid.srchCnd
      let oldPage = srchCnd.page
      let oldRowIndex = this.listGrid.rowIndex

      if (moveStr === 'previous') {
        this.listGrid.rowIndex = this.listGrid.rowIndex - 1
      } else {
        this.listGrid.rowIndex = this.listGrid.rowIndex + 1
      }
      if (this.listGrid.rowIndex < 0) {
        this.listGrid.rowIndex = srchCnd.pageSize - 1
        srchCnd.page = srchCnd.page - 1
      } else if (srchCnd.pageSize - 1 < this.listGrid.rowIndex) {
        this.listGrid.rowIndex = 0
        srchCnd.page = srchCnd.page + 1
      }
      if (srchCnd.page === 0 || oldPage === srchCnd.page || Math.ceil((this.listGrid.total / srchCnd.pageSize)) < srchCnd.page) {
        if (srchCnd.page === 0 || (this.listGrid.data.length - 1 < this.listGrid.rowIndex)) {
          this.listGrid.rowIndex = oldRowIndex
        }
        srchCnd.page = oldPage
        this.getRowData(this.listGrid.data[this.listGrid.rowIndex].id)
        return
      }
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let searchName = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        searchName = srchCnd.filters[1].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        searchParam: searchParam,
        searchName: searchName}
      }
      this.$http.get(
        '/episode/api/system/user',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    }
  },
  mounted: function () {
    this.initialize()
    // this.getListData()
  },
  beforeDestroy () {
    this.listGrid.data = []
    this.roleGrid.data = []
    this.authorityGrid.data = []
    this.filterGrid.data = []
    this.userStatusSelect = []
    this.orgSelect = []
    this.langSelect = []
    this.labels = {}
    this.authorities = {}
    this.validMsg = {}
    this.$nextTick(function () {
      this.listGridFlag = false
    })
  }
}

</script>
