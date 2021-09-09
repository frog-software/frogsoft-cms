//+-------------------------------------------------------------------------
//
//  // ---- What is this file about? --- //
//
//  File:       article.ts
//
//  Directory:  src/services
//
//  History:    9月-05-2021   QQK  Created
//
//--------------------------------------------------------------------------

import http                                     from 'utils/http';
import { pagedModelSimplifier }                 from 'utils/common';
import { JavaPagedModel, SimplifiedPagedModel } from 'types/common';
import { Article }                              from 'types/article';

const ANNOUNCEMENT_V1_URL = '/v1/home/announcements';

export const getAnnouncementList = () => new Promise<Article[]>((resolve, reject) => {
  http.get<JavaPagedModel<Article>>(ANNOUNCEMENT_V1_URL)
    .then((res) => {
      const simplifiedModel = pagedModelSimplifier(res);
      resolve(simplifiedModel.list);
    })
    .catch((err) => {
      if (err.status !== 404) {
        reject(err);
      }
    });
});

export default {
  getAnnouncementList,
};
