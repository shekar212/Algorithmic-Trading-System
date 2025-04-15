import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiscoverComponent } from './discover/discover.component';
import { LoginComponent } from './login/login.component';
import { StrategyMaComponent } from './strategy-ma/strategy-ma.component';
import { StrategyMetadataComponent } from './strategy-metadata/strategy-metadata.component';
import { LogoutComponent } from './logout/logout.component';
import { UserStrategyComponent } from './user-strategy/user-strategy.component';
import { UserOrdersComponent } from './user-orders/user-orders.component';

const routes: Routes = [{path:"login",component:LoginComponent},
{path:"",redirectTo:"login",pathMatch:'full'},
{path:"subscribe/:strategyId",component:StrategyMetadataComponent},
{path:"discover",component:DiscoverComponent},
{path:"logout",component:LogoutComponent},
{path:"subscribe/strategy-ma/:dataId",component:StrategyMaComponent},
{path:"my-strategies",component:UserStrategyComponent},
{path:"user-orders/:dataId",component:UserOrdersComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
//{path:"",redirectTo:"login",pathMatch:'full'}