import Vue from 'vue'
import Router from 'vue-router'
import VueCookie from 'vue-cookie'
import store from '@/store/store'

// Containers
const DefaultContainer = () => import('@/containers/DefaultContainer')

// Login & Dashboard
const Gate = () => import('@/views/gate/Gate')

// Monitor
const MonitorDashboard = () => import('@/views/monitor/MonitorDashboard')
const CiMonitorDetail = () => import('@/views/monitor/cidetail/CiMonitorDetail')
// const Alarm = () => import('@/views/monitor/alarm/Alarm')
const Event = () => import('@/views/monitor/event/Event')
const E2E = () => import('@/views/monitor/e2e/E2E')

// asset
const AssetDashboard = () => import('@/views/asset/DashboardAsset')
const Location = () => import('@/views/asset/location/Location')
const CI = () => import('@/views/asset/resource/ci/CI')
const CIhis = () => import('@/views/asset/resource/cihis/CIhis')
const DisCI = () => import('@/views/asset/resource/disci/DisCI')
const ExcelCI = () => import('@/views/asset/resource/excelci/ExcelCI')
const CIsw = () => import('@/views/asset/resource/cisw/CIsw')
const CIswList = () => import('@/views/asset/resource/ciswlist/CIswList')
const License = () => import('@/views/asset/licensemgmnt/license/License')
const LicenseUse = () => import('@/views/asset/licensemgmnt/licenseuse/LicenseUse')
const Swcat = () => import('@/views/asset/management/swcat/Swcat')
const Conversion = () => import('@/views/asset/management/conversion/Conversion')
const Company = () => import('@/views/asset/management/company/Company')
const Service = () => import('@/views/asset/servicemgmnt/service/Service')
const ServiceApp = () => import('@/views/asset/servicemgmnt/serviceapp/SeviceApp')

// System
const User = () => import('@/views/system/user/User')
const UserGroup = () => import('@/views/system/usergroup/UserGroup')
const Application = () => import('@/views/system/application/Application')
const Organization = () => import('@/views/system/organization/Organization')
const Domain = () => import('@/views/system/domain/Domain')
const Role = () => import('@/views/system/role/Role')
const Classification = () => import('@/views/system/classification/Classification')
const Navigator = () => import('@/views/system/navigator/Navigator')
const Scheduler = () => import('@/views/system/scheduler/Scheduler')
const Workflow = () => import('@/views/system/workflow/Workflow')
const Action = () => import('@/views/system/action/Action')
const Assignment = () => import('@/views/system/assignment/Assignment')
/*
// Views Episode etc
const DEVICE = () => import('@/components/device/Device')
const MAIN = () => import('@/components/main/Main')
const ABOUT = () => import('@/components/about/About')
const SIGNUP = () => import('@/components/signup/SignUp')
const FINDID = () => import('@/components/findid/FindId')

// Views Colors
const Colors = () => import('@/views/theme/Colors')
const Typography = () => import('@/views/theme/Typography')

// Views Chart
const Charts = () => import('@/views/Charts')
const Widgets = () => import('@/views/Widgets')

// Views - Components
const Cards = () => import('@/views/base/Cards')
const Forms = () => import('@/views/base/Forms')
const Switches = () => import('@/views/base/Switches')
const Tables = () => import('@/views/base/Tables')
const Tabs = () => import('@/views/base/Tabs')
const Breadcrumbs = () => import('@/views/base/Breadcrumbs')
const Carousels = () => import('@/views/base/Carousels')
const Collapses = () => import('@/views/base/Collapses')
const Jumbotrons = () => import('@/views/base/Jumbotrons')
const ListGroups = () => import('@/views/base/ListGroups')
const Navs = () => import('@/views/base/Navs')
const Navbars = () => import('@/views/base/Navbars')
const Paginations = () => import('@/views/base/Paginations')
const Popovers = () => import('@/views/base/Popovers')
const ProgressBars = () => import('@/views/base/ProgressBars')
const Tooltips = () => import('@/views/base/Tooltips')

// Views - Buttons
const StandardButtons = () => import('@/views/buttons/StandardButtons')
const ButtonGroups = () => import('@/views/buttons/ButtonGroups')
const Dropdowns = () => import('@/views/buttons/Dropdowns')
const BrandButtons = () => import('@/views/buttons/BrandButtons')

// Views - Icons
const Flags = () => import('@/views/icons/Flags')
const FontAwesome = () => import('@/views/icons/FontAwesome')
const SimpleLineIcons = () => import('@/views/icons/SimpleLineIcons')
const CoreUIIcons = () => import('@/views/icons/CoreUIIcons')

// Views - Notifications
const Alerts = () => import('@/views/notifications/Alerts')
const Badges = () => import('@/views/notifications/Badges')
const Modals = () => import('@/views/notifications/Modals')

// Views - Pages
const Page404 = () => import('@/views/pages/Page404')
const Page500 = () => import('@/views/pages/Page500')
const Login = () => import('@/views/pages/Login')
const Register = () => import('@/views/pages/Register')

// Users
const Users = () => import('@/views/users/Users')
*/

Vue.use(Router)

const requireAuth = (name) => (to, from, next) => {
  let auth = store.getters.getAuth
  let readAuth = auth.some(r => {
    return r.authorityId === name
  })
  if (readAuth) {
    next()
  }
}

export default new Router({
  mode: 'hash', // https://router.vuejs.org/api/#mode
  // mode: 'history',
  linkActiveClass: 'open active',
  scrollBehavior: () => ({ y: 0 }),
  routes: [
    { path: '/gate',
      name: 'GATE',
      component: Gate,
      meta: {authRequired: false},
      beforeEnter: function (to, from, next) {
        if (VueCookie.get('kaiicamsecret') === null) {
          next()
        } else {
          next('/')
        }
      }
    },
    {
      path: '/',
      redirect: '/monitor/dashboard',
      name: '',
      meta: {authRequired: true},
      component: DefaultContainer,
      children: [
        {
          path: 'monitor',
          redirect: '/monitor/dashboard',
          name: 'MONITORING',
          meta: {authRequired: true},
          component: {
            render (c) { return c('router-view') }
          },
          children: [
            {
              path: 'dashboard',
              name: 'MONITORDASHBOARD',
              meta: {authRequired: true},
              component: MonitorDashboard
            },
            {
              path: 'dashboard/:id',
              name: 'CIMONITORDETAIL',
              meta: {authRequired: true},
              component: CiMonitorDetail,
              beforeEnter: requireAuth('MONITORDETAIL_READ')
            },
            {
              path: 'event',
              name: 'EVENT',
              meta: {authRequired: true},
              component: Event,
              beforeEnter: requireAuth('EVENT_READ')
            },
            {
              path: 'e2e',
              name: 'E2E',
              meta: {authRequired: true},
              component: E2E,
              beforeEnter: requireAuth('E2E_READ')
            }
          ]
        },
        {
          path: 'infra',
          redirect: '/infra/dashboard',
          name: 'INFRA',
          meta: {authRequired: true},
          component: {
            render (c) { return c('router-view') }
          },
          children: [
            {
              path: 'dashboard',
              name: 'ASSETDASHBOARD',
              meta: {authRequired: true},
              component: AssetDashboard,
              beforeEnter: requireAuth('ASSETDASHBOARD_READ')
            },
            {
              path: 'licensemgmnt',
              redirect: '/infra/licensemgmnt',
              name: 'LICENSEMGMNT',
              meta: {authRequired: true},
              component: {
                render (c) { return c('router-view') }
              },
              children: [
                {
                  path: 'license',
                  name: 'LICENSE',
                  meta: {authRequired: true},
                  component: License,
                  beforeEnter: requireAuth('LICENSE_READ')
                },
                {
                  path: 'licenseuse',
                  name: 'LICENSEUSE',
                  meta: {authRequired: true},
                  component: LicenseUse,
                  beforeEnter: requireAuth('LICENSEUSE_READ')
                }
              ]
            },
            {
              path: 'servicemgmnt',
              redirect: '/infra/servicemgmnt',
              name: 'SERVICEMGMNT',
              meta: {authRequired: true},
              component: {
                render (c) { return c('router-view') }
              },
              children: [
                {
                  path: 'service',
                  name: 'SERVICE',
                  meta: {authRequired: true},
                  component: Service,
                  beforeEnter: requireAuth('SERVICE_READ')
                },
                {
                  path: 'serviceapp',
                  name: 'SERVICEAPP',
                  meta: {authRequired: true},
                  component: ServiceApp,
                  beforeEnter: requireAuth('SERVICEAPP_READ')
                }
              ]
            },
            {
              path: 'resource',
              redirect: '/infra/resource',
              name: 'RESOURCE',
              meta: {authRequired: true},
              component: {
                render (c) { return c('router-view') }
              },
              children: [
                {
                  path: 'ci',
                  name: 'CI',
                  meta: {authRequired: true},
                  component: CI,
                  beforeEnter: requireAuth('CI_READ')
                },
                {
                  path: 'cihis',
                  name: 'CIHIS',
                  meta: {authRequired: true},
                  component: CIhis,
                  beforeEnter: requireAuth('CIHIS_READ')
                },
                {
                  path: 'disci',
                  name: 'DISCI',
                  meta: {authRequired: true},
                  component: DisCI,
                  beforeEnter: requireAuth('DISCI_READ')
                },
                {
                  path: 'cisw',
                  name: 'CISW',
                  meta: {authRequired: true},
                  component: CIsw,
                  beforeEnter: requireAuth('CISW_READ')
                },
                {
                  path: 'ciswlist',
                  name: 'CISWLIST',
                  meta: {authRequired: true},
                  component: CIswList,
                  beforeEnter: requireAuth('CISWLIST_READ')
                },
                {
                  path: 'excelci',
                  name: 'EXCELCI',
                  meta: {authRequired: true},
                  component: ExcelCI,
                  beforeEnter: requireAuth('EXCELCI_READ')
                }
              ]
            },
            {
              path: 'management',
              redirect: '/infra/management',
              name: 'INFRAMGMNT',
              meta: {authRequired: true},
              component: {
                render (c) { return c('router-view') }
              },
              children: [
                {
                  path: 'swcat',
                  name: 'SWCAT',
                  meta: {authRequired: true},
                  component: Swcat,
                  beforeEnter: requireAuth('SWCAT_READ')
                },
                {
                  path: 'conversion',
                  name: 'CONVERSION',
                  meta: {authRequired: true},
                  component: Conversion,
                  beforeEnter: requireAuth('CONVERSION_READ')
                },
                {
                  path: 'company',
                  name: 'COMPANY',
                  meta: {authRequired: true},
                  component: Company,
                  beforeEnter: requireAuth('COMPANY_READ')
                }
              ]
            }
          ]
        },
        {
          path: 'system',
          redirect: '/system/application',
          name: 'SYSTEM',
          meta: {authRequired: true},
          component: {
            render (c) { return c('router-view') }
          },
          children: [
            {
              path: 'application',
              name: 'APPLICATION',
              meta: {authRequired: true},
              component: Application,
              beforeEnter: requireAuth('APPLICATION_READ')
            },
            {
              path: 'scheduler',
              name: 'SCHEDULER',
              meta: {authRequired: true},
              component: Scheduler,
              beforeEnter: requireAuth('SCHEDULER_READ')
            },
            {
              path: 'user',
              name: 'USER',
              meta: {authRequired: true},
              component: User,
              beforeEnter: requireAuth('USER_READ')
            },
            {
              path: 'usergroup',
              name: 'USERGROUP',
              meta: {authRequired: true},
              component: UserGroup,
              beforeEnter: requireAuth('USERGROUP_READ')

            },
            {
              path: 'organization',
              name: 'ORGANIZATION',
              meta: {authRequired: true},
              component: Organization,
              beforeEnter: requireAuth('ORGANIZATION_READ')
            },
            {
              path: 'role',
              name: 'ROLE',
              meta: {authRequired: true},
              component: Role,
              beforeEnter: requireAuth('ROLE_READ')
            },
            {
              path: 'code',
              name: 'DOMAIN',
              meta: {authRequired: true},
              component: Domain,
              beforeEnter: requireAuth('DOMAIN_READ')
            },
            {
              path: 'classification',
              name: 'CLASSIFICATION',
              meta: {authRequired: true},
              component: Classification,
              beforeEnter: requireAuth('CLASSIFICATION_READ')
            },
            {
              path: 'menu',
              name: 'NAVIGATOR',
              meta: {authRequired: true},
              component: Navigator,
              beforeEnter: requireAuth('NAVIGATOR_READ')
            },
            {
              path: 'location',
              name: 'LOCATION',
              meta: {authRequired: true},
              component: Location,
              beforeEnter: requireAuth('LOCATION_READ')
            },
            {
              path: 'workflow',
              name: 'WORKFLOW',
              meta: {authRequired: true},
              component: Workflow,
              beforeEnter: requireAuth('WORKFLOW_READ')
            },
            {
              path: 'action',
              name: 'ACTION',
              meta: {authRequired: true},
              component: Action,
              beforeEnter: requireAuth('ACTION_READ')
            },
            {
              path: 'assignment',
              name: 'ASSIGNMENT',
              meta: {authRequired: true},
              component: Assignment,
              beforeEnter: requireAuth('ASSIGNMENT_READ')
            }
          ]
        }
      ]
    }
  ]
})
