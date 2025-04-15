import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DiscoverComponent } from './discover/discover.component';
import { UserHeaderComponent } from './user-header/user-header.component';
import { StrategyMaComponent } from './strategy-ma/strategy-ma.component';
import { StrategyMetadataComponent } from './strategy-metadata/strategy-metadata.component';
import { LogoutComponent } from './logout/logout.component';
import { UserStrategyComponent } from './user-strategy/user-strategy.component';
import { UserOrdersComponent } from './user-orders/user-orders.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DiscoverComponent,
    UserHeaderComponent,
    StrategyMaComponent,
    StrategyMetadataComponent,
    LogoutComponent,
    UserStrategyComponent,
    UserOrdersComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
