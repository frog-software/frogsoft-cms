//+-------------------------------------------------------------------------
//
//  文章列表
//
//  File:       index.tsx
//
//  Directory:  src/pages/Article/ArticleList
//
//  History:    9月-03-2021   QQK  Created
//
//--------------------------------------------------------------------------

import React, { FC, useEffect, useState }       from 'react';
import { Article }                              from 'types/article';
import Block                                    from 'components/Block';
import {
  Button, message, Space, Table,
}                                               from 'antd';
import http                                     from 'utils/http';
import { JavaPagedModel, SimplifiedPagedModel } from 'types/common';
import { pagedModelSimplifier }                 from 'utils/common';
import { useQuery }                             from 'react-query';
import { useHistory }                           from 'react-router';
import { getArticleList }                       from 'services/article';

// const data = [
//   {
//     id: 66,
//     author: {
//       email: 'y.mvibxw@erqgb.py',
//       username: '吕明',
//       roles: [
//         'ROLE_ADMIN',
//         'ROLE_USER',
//       ],
//     },
//     status: 'NORMAL',
//     publishDate: '1981-01-19 16:53:16',
//     updateDate: '2016-07-12 16:27:04',
//     views: 4283877329703202,
//     title: '己体把按这养边者着算加改商业究构北。',
//     description: '了包元头铁位设体离算你长史团开话。',
//     cover: 'http://dummyimage.com/468x60',
//     likes: 1273300982987200,
//     favorites: 4100051159772794,
//   },
//   {
//     id: 90,
//     author: {
//       email: 'f.ewspypb@fbnoqfbdw.to',
//       username: '邓强',
//       roles: [
//         'ROLE_ADMIN',
//         'ROLE_USER',
//       ],
//     },
//     status: 'NORMAL',
//     publishDate: '2018-11-13 20:58:14',
//     updateDate: '1989-10-02 16:36:08',
//     views: 6904183919565666,
//     title: '响以做铁识民以自生出子起从。',
//     description: '声关什西南将向海计特日改极济史。',
//     cover: 'http://dummyimage.com/125x125',
//     likes: 7663311257487064,
//     favorites: 4635370963450182,
//   },
//   {
//     id: 31,
//     author: {
//       email: 'i.oucjx@aafriot.ye',
//       username: '陈明',
//       roles: [
//         'ROLE_USER',
//         'ROLE_ADMIN',
//       ],
//     },
//     status: 'BLOCKED',
//     publishDate: '2008-07-21 08:44:43',
//     updateDate: '1970-02-27 11:45:30',
//     views: 8437205898730658,
//     title: '连候我上合造却权图么世学条果去至。',
//     description: '比变件那按门记快断改办派油满断。',
//     cover: 'http://dummyimage.com/120x240',
//     likes: 1941214943792078,
//     favorites: 3947843683658152,
//   },
//   {
//     id: 79,
//     author: {
//       email: 'n.zukd@zjfhk.ag',
//       username: '曹霞',
//       roles: [
//         'ROLE_USER',
//         'ROLE_ADMIN',
//       ],
//     },
//     status: 'BLOCKED',
//     publishDate: '2006-05-07 23:41:06',
//     updateDate: '2017-12-27 12:51:44',
//     views: 4569196497073036,
//     title: '部已工的委信提眼断心置前平活等。',
//     description: '样在例成水想性队改时克气备龙。',
//     cover: 'http://dummyimage.com/180x150',
//     likes: 1492327820220490,
//     favorites: 1556094858126586,
//   },
//   {
//     id: 28,
//     author: {
//       email: 'j.kkirwvb@gvygdkccx.bg',
//       username: '贾磊',
//       roles: [
//         'ROLE_USER',
//       ],
//     },
//     status: 'NORMAL',
//     publishDate: '1984-11-23 03:47:45',
//     updateDate: '1978-12-18 01:01:50',
//     views: 8141076698394888,
//     title: '片商众厂头严方任次布院要性展接。',
//     description: '任青也非较议料低区知风规位原响专东。',
//     cover: 'http://dummyimage.com/120x600',
//     likes: 3278789020721732,
//     favorites: 4044612342336410,
//   },
//   {
//     id: 67,
//     author: {
//       email: 'p.buuhnn@dfrerhmupf.lb',
//       username: '沈涛',
//       roles: [
//         'ROLE_USER',
//       ],
//     },
//     status: 'BLOCKED',
//     publishDate: '1979-06-18 06:06:22',
//     updateDate: '1974-04-23 12:53:34',
//     views: 1310708183768456,
//     title: '时发南布元回又团先去最布目。',
//     description: '院八老况老拉连复十物原信因话上大的。',
//     cover: 'http://dummyimage.com/120x600',
//     likes: 4159781621601922,
//     favorites: 193681022029452,
//   },
//   {
//     id: 6,
//     author: {
//       email: 'o.ucs@irmplb.fj',
//       username: '沈明',
//       roles: [
//         'ROLE_USER',
//         'ROLE_ADMIN',
//       ],
//     },
//     status: 'BLOCKED',
//     publishDate: '1987-06-14 19:19:53',
//     updateDate: '1986-06-03 17:12:10',
//     views: 8430843536938252,
//     title: '界布术图计十将复高价统流那安界写况。',
//     description: '代你日温究备的包容合有然立经王西给国。',
//     cover: 'http://dummyimage.com/180x150',
//     likes: 502110888325088,
//     favorites: 51228433386938,
//   },
// ];

const ArticleList: FC = () => {
  const [articleList, setAricleList]  = useState<Article[]>();
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [pageSize, setPageSize]       = useState<number>(10);
  const [totalItems, setTotalItems]   = useState<number>();
  const history                       = useHistory();

  const tableColumns = [
    {
      key: 'id',
      dataIndex: 'id',
      title: '文章ID',
      sorter: {
        compare: (a, b) => a.id - b.id,
        multiple: 2,
      },
    },
    {
      key: 'title',
      dataIndex: 'title',
      title: '标题',
    },
    {
      key: 'author',
      dataIndex: ['author', 'username'],
      title: '作者',
    },
    {
      key: 'publishDate',
      dataIndex: 'publishDate',
      title: '发布时间',
      sorter: {
        compare: (a, b) => a.publishDate - b.publishDate,
        multiple: 1,
      },
    },
    {
      key: 'action',
      title: '执行操作',
      render: (article) => (
        <Space>
          <Button onClick={() => {
            history.push(`/articles/${article.id}`);
          }}
          >
            查看
          </Button>
          <Button>删除</Button>
        </Space>
      ),
    },
  ];

  // useEffect(() => {
  //   (async () => {
  //     const data = await http.get<JavaPagedModel<Article>>('http://127.0.0.1:4523/mock/419258/v1/articles');
  //     // const data2 = pagedModelSimplifier(data);
  //     setAricleList(data._embedded.articleDtoList);
  //     setTotalItems(data.page.totalElements);
  //     // setIsLoading(false);
  //   })();
  // }, [currentPage]);

  const { isLoading, data } = useQuery(
    ['articleList', currentPage - 1, pageSize],
    getArticleList,
    {
      staleTime: 30000,
      onError: (err) => {
        message.error(String(err));
      },
    },
  );

  useEffect(() => {
    if (!data) return;

    setAricleList(data.list);
    setTotalItems(data.page.total);
  }, [data]);

  return (
    <>
      <Block title="文章管理">
        <Table
          rowKey="id"
          loading={isLoading}
          columns={tableColumns}
          dataSource={articleList}
          pagination={{
            pageSize,
            total: totalItems,
            showSizeChanger: true,
            current: currentPage,
            onChange: (page, size) => {
              setCurrentPage(size === pageSize ? page : 1);
              setPageSize(size);
            },
          }}
        />
      </Block>
    </>
  );
};

export default ArticleList;
