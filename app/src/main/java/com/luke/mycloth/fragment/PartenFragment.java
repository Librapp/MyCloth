package com.luke.mycloth.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.luke.mycloth.ClothEditActivity;
import com.luke.mycloth.R;
import com.luke.mycloth.adapter.PhotoAdapter;
import com.luke.mycloth.bean.Cloth;
import com.luke.mycloth.dao.ClothDao;
import com.luke.mycloth.util.DialogUtil;
import com.luke.mycloth.util.PhotoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PartenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PartenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartenFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int category;
    private GridView gridView;

    private List<Cloth> datas = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public PartenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PartenFragment.
     */
    public static PartenFragment newInstance(int category) {
        PartenFragment fragment = new PartenFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_part, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                PhotoUtil.pickPhoto(PartenFragment.this).show();
            }
        });
        gridView = (GridView) v.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ClothEditActivity.class);
                intent.putExtra("photo", datas.get(i));
                startActivity(intent);
            }
        });
        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogUtil.doWithPhoto(getActivity(), datas.get(i)).show();
                return true;
            }
        });
        datas = new ClothDao(getActivity()).queryByCategory(category);
        gridView.setAdapter(new PhotoAdapter(getActivity(), datas, gridView));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 因为两种方式都用到了startActivityForResult方法，
         * 这个方法执行完后都会执行onActivityResult方法， 所以为了区别到底选择了那个方式获取图片要进行判断，
         * 这里的requestCode跟startActivityForResult里面第二个参数对应
         */
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.REQUEST_ABLUM:
                    Uri originalUri = data.getData();
                    PhotoUtil.startPhotoZoom(this, originalUri);
                    break;
                case PhotoUtil.REQUEST_CAMERA:
                    PhotoUtil.startPhotoZoom(this, PhotoUtil.temp);
                    break;
                case PhotoUtil.REQUEST_CROP:
                    Cloth photo = new Cloth();
                    photo.filepath = PhotoUtil.result.getPath();
                    photo.category = category;
                    Intent i = new Intent(getActivity(), ClothEditActivity.class);
                    i.putExtra("photo", photo);
                    startActivity(i);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
