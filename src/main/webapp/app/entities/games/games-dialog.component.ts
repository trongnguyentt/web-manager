import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Games } from './games.model';
import { GamesPopupService } from './games-popup.service';
import { GamesService } from './games.service';

@Component({
    selector: 'jhi-games-dialog',
    templateUrl: './games-dialog.component.html'
})
export class GamesDialogComponent implements OnInit {

    games: Games;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private gamesService: GamesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.games.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gamesService.update(this.games));
        } else {
            this.subscribeToSaveResponse(
                this.gamesService.create(this.games));
        }
    }

    private subscribeToSaveResponse(result: Observable<Games>) {
        result.subscribe((res: Games) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Games) {
        this.eventManager.broadcast({ name: 'gamesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-games-popup',
    template: ''
})
export class GamesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gamesPopupService: GamesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gamesPopupService
                    .open(GamesDialogComponent as Component, params['id']);
            } else {
                this.gamesPopupService
                    .open(GamesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
