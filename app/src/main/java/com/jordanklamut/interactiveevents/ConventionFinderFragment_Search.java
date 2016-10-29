package com.jordanklamut.interactiveevents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.DialogFragment;


public class ConventionFinderFragment_Search extends Fragment{

    EditText etConName;
    EditText etConCode;
    EditText etConCity;
    Spinner sConState;
    Spinner sConWithin;
    EditText etConStartDate;
    EditText etConEndDate;

    public ConventionFinderFragment_Search() {
    }

    public static ConventionFinderFragment_Search newInstance() {
        ConventionFinderFragment_Search fragment = new ConventionFinderFragment_Search();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.convention_finder_fragment_search, container, false);

        Button btnSearch = (Button) v.findViewById(R.id.btn_search);
        etConName = (EditText) v.findViewById(R.id.et_search_con_name);
        etConCode = (EditText) v.findViewById(R.id.et_search_con_code);
        etConCity = (EditText) v.findViewById(R.id.et_search_start_city);
        sConState = (Spinner) v.findViewById(R.id.s_search_start_state);
        sConWithin = (Spinner) v.findViewById(R.id.s_search_within);
        etConStartDate = (EditText) v.findViewById(R.id.et_search_start_date);
        etConEndDate = (EditText) v.findViewById(R.id.et_search_end_date);

        //DISABLE KEYBOARD FOR DATES
        etConStartDate.setInputType(InputType.TYPE_NULL);
        etConEndDate.setInputType(InputType.TYPE_NULL);

        //TODO - NEED TO IMPLEMENT FUNCTIONS, DISABLED UNTIL DONE
        etConCode.setEnabled(false);
        sConWithin.setEnabled(false);


        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                String[] conInfo = {
                    etConName.getText().toString(),
                    etConCode.getText().toString(),
                    etConCity.getText().toString(),
                    sConState.getSelectedItem().toString(),
                    sConWithin.getSelectedItem().toString(),
                    etConStartDate.getText().toString(),
                    etConEndDate.getText().toString()
                };

                Intent i = new Intent(getContext(), ConventionFinderActivity_SearchResults.class);
                i.putExtra("conInfo", conInfo);
                startActivity(i);
            }
        });

        etConStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month++;
                            etConStartDate.setText(month + "/" + dayOfMonth + "/" + year);
                        }
                    };
                    newFragment.show(getActivity().getFragmentManager(), "Date Picker");
                }
            }
        });

        etConStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        etConStartDate.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                };
                newFragment.show(getActivity().getFragmentManager(), "Date Picker");

            }
        });

        etConEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month++;
                            etConEndDate.setText(month + "/" + dayOfMonth + "/" + year);
                        }
                    };
                    newFragment.show(getActivity().getFragmentManager(), "Date Picker");
                }
                //new DatePickerDialog(getActivity(), from_dateListener, from_year, from_month, from_day);
                //DialogFragment newFragment = new DatePickerFragment();
                //newFragment.show(getActivity().getFragmentManager(),"Date Picker");
            }
        });

        etConEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        etConEndDate.setText(month + "/" + dayOfMonth + "/" + year);
                    }
                };
                newFragment.show(getActivity().getFragmentManager(), "Date Picker");

            }
        });

        //from_dateListener = new DatePickerDialog.OnDateSetListener() {
        //    @Override
        //    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //        String stringOfDate = month + "/" + dayOfMonth + "/" + year;
        //        etConStartDate.setText(stringOfDate);
        //    }
        //};
//
        //to_dateListener = new DatePickerDialog.OnDateSetListener() {
        //    @Override
        //    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //        String stringOfDate = month + "/" + dayOfMonth + "/" + year;
        //        etConEndDate.setText(stringOfDate);
        //    }
        //};

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
