package com.example.strnarazr2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OZFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OZFragment extends Fragment {
    private static final String TAG = "OZFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static double korD = 0;
    //переменная для различения с каких полей брать корректуру
    static boolean komPoRaz = true;
    EditText pXop;
    EditText pYop;
    EditText pXc;
    EditText pYc;
    EditText pdXtus;
    EditText pvd;
    EditText pdX;
    EditText pdY;
    Button btnReshKorr;
    TextView pVuvDPrDd;
    TextView pVuvDYglomer;
    Button btnDobavRAxrVSeriy;
    int schetckikRazruvov = 0;
    int schetchikPlus = 0;
    int schetchikMinus = 0;
    TextView pVuvKolRazr;
    TextView pVuvPlus;
    TextView pVuvMinus;
    TextView pVuvDXSr;
    TextView pVuvDYSr;
    int schetchikDX = 0;
    int schetchikDY = 0;
    TextView pVuvSootnshenie;
    Button btnKorrPoSerii;
    TextView pVuvKorrPricPoVd;
    TextView pVuvKorrDovPoSerii;
    TextView poleZapisStrelbu;
    TextView pPozuvnoi;
    TextView pNZeli;
    Button zapisDannueOP;
    Button vuhod;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OZFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OZFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OZFragment newInstance(String param1, String param2) {
        OZFragment fragment = new OZFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint({"SetTextI18n", "MissingInflatedId", "UseRequireInsteadOfGet"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    public double[] OGZ(double Xc, double Yc, double Xb, double Yb) {
        double dxc = Xc - Xb;
        double dyc = Yc - Yb;
        double Dc = Math.sqrt(Math.pow(dxc, 2) + Math.pow(dyc, 2));
        double Ac = Math.abs(Math.atan(dyc / (dxc)) / Math.PI * 30) * 100;
        double Ygolc = 0;
        if (dxc > 0 && dyc > 0) {
            Ygolc = Math.round(Ac);
        } else if (dxc < 0 && dyc > 0) {
            Ygolc = Math.round(3000 - Ac);
        } else if (dxc < 0 && dyc < 0) {
            Ygolc = Math.round(3000 + Ac);
        } else if (dxc > 0 && dyc < 0) {
            Ygolc = Math.round(6000 - Ac);
        }
        return new double[]{Dc, Ygolc};
    }

    private void showSimpleDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(requireContext());

        //Настройка сообщения вручную и выполнение действия по нажатию кнопки
        builder.setMessage("Отправить корректуру в команду?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    public void onClick(DialogInterface dialog, int id) {
                        //  Действие для кнопки "Да"
                        if (komPoRaz) komandaPoRazruvy();
                        if (!komPoRaz) komandaPoSerii();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Действие для кнопки "Нет"
                        Toast.makeText(requireContext().getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
        //Создание диалогового окна
        AlertDialog alert = builder.create();
        //Установка заголовка вручную
        alert.setTitle("КОМАНДА:");
        alert.show();
    }

    String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @SuppressLint("SetTextI18n")
    public void komandaPoRazruvy() {
        //смена цвета
        String str = getColoredSpanned("Команда: Пр/Д " + pVuvDPrDd.getText().toString()
                + ". Угломер: " + pVuvDYglomer.getText().toString(), "#C81508");
        poleZapisStrelbu.append(Html.fromHtml(str));
        poleZapisStrelbu.append("\n");
        poleZapisStrelbu.append("\n");
    }

    @SuppressLint("SetTextI18n")
    public void komandaPoSerii() {
        String str = getColoredSpanned("Команда: Пр/Д: " + pVuvKorrPricPoVd.getText().toString()
                + ". Угломер: " + pVuvKorrDovPoSerii.getText().toString(), "#C81508");
        poleZapisStrelbu.append(Html.fromHtml(str));
        poleZapisStrelbu.append("\n");
        poleZapisStrelbu.append("\n");
    }

    //расчет корректуры по серии
    @SuppressLint("SetTextI18n")
    public void poschitKorPoSerii() {
        String sootnohen = pVuvSootnshenie.getText().toString();
        double vd = Double.parseDouble(pvd.getText().toString());
        double dXtus = Double.parseDouble(pdXtus.getText().toString());
        double korPric = 0.0;
        if (!sootnohen.equals("")) {
            if (sootnohen.equals("1/1")) {
                korPric = 0;
            } else if (sootnohen.equals("1/2")) {
                korPric = 0;
            } else if (sootnohen.equals("1/3")) {
                korPric = vd / dXtus;
            } else if (sootnohen.equals("1/4")) {
                korPric = (vd * 2) / dXtus;
            } else {
                korPric = (vd * 2) / dXtus;
            }
            //определяем знак корректуры
            if (schetchikPlus > schetchikMinus)
                korPric *= -1;

            if (korPric > 0) {
                pVuvKorrPricPoVd.setText("+" + Math.round(korPric) + "/" + "+" + Math.round(korPric * dXtus));
            } else {
                pVuvKorrPricPoVd.setText(Math.round(korPric) + "/" + Math.round(korPric * dXtus));
            }
            String[] korr = reshKorr((double) schetchikDX / schetckikRazruvov, (double) schetchikDY / schetckikRazruvov);
            pVuvKorrDovPoSerii.setText(korr[1]);
        }
        poleZapisStrelbu.append("По серии." + "\n" + "Корректура. Пр/Д: " + pVuvKorrPricPoVd.getText().toString()
                + ". Угломер: " + pVuvKorrDovPoSerii.getText().toString() + ". Соотношение: "
                + pVuvSootnshenie.getText().toString() + ". Плюс: " + schetchikPlus + ", Минус: "
                + schetchikMinus + ". К-во разрывов в серии: " + schetckikRazruvov + ". Среднее dX: "
                + schetchikDX / schetckikRazruvov + ", dY: " + schetchikDY / schetckikRazruvov + "\n");

        schetckikRazruvov = 0;
        schetchikPlus = 0;
        schetchikMinus = 0;
        schetchikDX = 0;
        schetchikDY = 0;

        //очищаем поля
        pVuvDXSr.setText("");
        pVuvDYSr.setText("");
        pVuvKolRazr.setText("");
        pVuvPlus.setText("");
        pVuvMinus.setText("");
        pVuvSootnshenie.setText("");
    }

    //посчитать соотношение
    @SuppressLint("SetTextI18n")
    public void getSootnoshenie() {
        int sootnoshenie;
        try {
            if (schetchikPlus > schetchikMinus) {
                sootnoshenie = schetchikPlus / schetchikMinus;
            } else if (schetchikPlus < schetchikMinus) {
                sootnoshenie = schetchikMinus / schetchikPlus;
            } else {
                sootnoshenie = 1;
            }
            if (sootnoshenie == 0) sootnoshenie = 1;
            pVuvSootnshenie.setText("1/" + sootnoshenie);
        } catch (Exception e) {
            Log.e(TAG + ".getSootnoshenie", e.getMessage());
            if (schetchikPlus == 0)
                pVuvSootnshenie.setText("0/" + schetchikMinus);
            if (schetchikMinus == 0)
                pVuvSootnshenie.setText("0/" + schetchikPlus);
        }
    }

    //добавить разрыв в серию
    public void setRazrVSeriu() {
        schetckikRazruvov++;
        pVuvKolRazr.setText(String.valueOf(schetckikRazruvov));
        int vd = Integer.parseInt(pvd.getText().toString());
        if (vd <= 10 && Math.abs(korD) <= 5) {
            schetchikPlus++;
            schetchikMinus++;
        } else if (vd <= 15 && Math.abs(korD) <= 8) {
            schetchikPlus++;
            schetchikMinus++;
        } else if (vd <= 25 && Math.abs(korD) <= 10) {
            schetchikPlus++;
            schetchikMinus++;
        } else if (vd <= 35 && Math.abs(korD) <= 15) {
            schetchikPlus++;
            schetchikMinus++;
        } else if (vd > 35 && Math.abs(korD) <= 20) {
            schetchikPlus++;
            schetchikMinus++;
        } else {
            if (korD > 0) {
                schetchikMinus++;
            } else {
                schetchikPlus++;
            }
        }
        pVuvPlus.setText(String.valueOf(schetchikPlus));
        pVuvMinus.setText(String.valueOf(schetchikMinus));
    }

    //вывод средн dx, dy
    public void setDxDyVSeriu() {
        schetchikDX = schetchikDX + Integer.parseInt(pdX.getText().toString());
        schetchikDY = schetchikDY + Integer.parseInt(pdY.getText().toString());
        int dxSr = schetchikDX / schetckikRazruvov;
        int dySr = schetchikDY / schetckikRazruvov;
        pVuvDXSr.setText(String.valueOf(dxSr));
        pVuvDYSr.setText(String.valueOf(dySr));
    }

    //расчет корректуры по dx, dy
    @SuppressLint("SetTextI18n")
    public String[] reshKorr(double dxr, double dyr) {
        double xop = Double.parseDouble(pXop.getText().toString());
        double yop = Double.parseDouble(pYop.getText().toString());
        double xc = Double.parseDouble(pXc.getText().toString());
        double yc = Double.parseDouble(pYc.getText().toString());

        double[] topoDannue = OGZ(xc, yc, xop, yop);
        double dCeli = topoDannue[0];
        double ygolCeli = topoDannue[1];

        double xr = xc + dxr;
        double yr = yc + dyr;
        topoDannue = OGZ(xr, yr, xop, yop);
        double dRazruva = topoDannue[0];
        double ygolRazruva = topoDannue[1];

        double ddaln = dCeli - dRazruva;
        double korDov = ygolCeli - ygolRazruva;
        double dXtus = Double.parseDouble(pdXtus.getText().toString());
        double korPric = ddaln / dXtus;
        korD = ddaln;
        if (ddaln > 0 && korDov > 0) {
            return new String[]{"+" + Math.round(korPric) + "/" + "+" + Math.round(ddaln), "+" + (Math.round(korDov))};
        } else if (ddaln > 0 && korDov <= 0) {
            return new String[]{"+" + Math.round(korPric) + "/" + "+" + Math.round(ddaln), String.valueOf(Math.round(korDov))};
        } else if (ddaln <= 0 && korDov <= 0) {
            return new String[]{Math.round(korPric) + "/" + Math.round(ddaln), String.valueOf(Math.round(korDov))};
        } else {
            return new String[]{Math.round(korPric) + "/" + Math.round(ddaln), "+" + (Math.round(korDov))};
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_z, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.frame), new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        });

        TabHost tabHost = view.findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.dannueOP);
        tabSpec.setIndicator("Данные ОП");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.strelba);
        tabSpec.setIndicator("Стрельба");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.zapisStrelbu);
        tabSpec.setIndicator("Запись стрельбы");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
        //инициализация полей
        pXop = view.findViewById(R.id.pXop);
        pYop = view.findViewById(R.id.pYop);
        pXc = view.findViewById(R.id.pXc);
        pYc = view.findViewById(R.id.pYc);
        pdXtus = view.findViewById(R.id.dXtus);
        pvd = view.findViewById(R.id.vd);
        pdX = view.findViewById(R.id.pdX);
        pdY = view.findViewById(R.id.pdY);
        pVuvDPrDd = view.findViewById(R.id.pVuvKorPricel);
        pVuvDYglomer = view.findViewById(R.id.pVuvKorYglomer);

        //мерцание
        @SuppressLint("ResourceType") final Animation animRotate = AnimationUtils.loadAnimation(getContext(), R.drawable.dr_mercanie);

        //для завершения работы
        vuhod = view.findViewById(R.id.btnVuxod);
        vuhod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animRotate);
                System.exit(0);
            }
        });

        poleZapisStrelbu = view.findViewById(R.id.editTextTextMultiLine);

        //инициализация кнопки реш корект
        btnReshKorr = view.findViewById(R.id.poscitatKorrektyry);
        btnReshKorr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animRotate);
                try {
                    String[] korr = OZFragment.this.reshKorr(Double.parseDouble(pdX.getText().toString()), Double.parseDouble(pdY.getText().toString()));
                    pVuvDPrDd.setText(korr[0]);
                    pVuvDYglomer.setText(korr[1]);
                } catch (Exception ex) {
                    Log.e("OZFragment.onCreateView", ex.getMessage());
                }
                komPoRaz = true;
                OZFragment.this.showSimpleDialog();
            }
        });
        //инициализация кнопки добавить в серию
        btnDobavRAxrVSeriy = view.findViewById(R.id.dobavitRazrVSeriy);
        btnDobavRAxrVSeriy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    v.startAnimation(animRotate);
                    setRazrVSeriu();
                    setDxDyVSeriu();
                    getSootnoshenie();
                    poleZapisStrelbu.append("Разрыв № " + schetckikRazruvov + ": dX: " + pdX.getText().toString()
                            + ", dY: " + pdY.getText().toString() + "\n" + "Корректура. Пр/Д: "
                            + pVuvDPrDd.getText().toString()
                            + ". Угломер: " + pVuvDYglomer.getText().toString() + "\n");
                } catch (Exception e) {
                }
            }
        });
        //инициализация полей по серии
        pVuvKolRazr = view.findViewById(R.id.pNrazruva);
        pVuvPlus = view.findViewById(R.id.pVuvPlus);
        pVuvMinus = view.findViewById(R.id.pVuvMinus);
        pVuvDXSr = view.findViewById(R.id.pVuvdXSr);
        pVuvDYSr = view.findViewById(R.id.pVuvdYSr);
        pVuvSootnshenie = view.findViewById(R.id.pVuvSootnosh);
        //посчитать корректуру  по серии
        btnKorrPoSerii = view.findViewById(R.id.poscitKorrPoSerii);
        btnKorrPoSerii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    v.startAnimation(animRotate);
                    poschitKorPoSerii();
                } catch (Exception e) {
                }
                komPoRaz = false;
                showSimpleDialog();
            }
        });
        pVuvKorrPricPoVd = view.findViewById(R.id.pVuvKorPricVd);
        pVuvKorrDovPoSerii = view.findViewById(R.id.pVuvKorYglVd);
        //запись стрельбы
        pPozuvnoi = view.findViewById(R.id.pPozuvnoi);
        pNZeli = view.findViewById(R.id.pNZeli);

        zapisDannueOP = view.findViewById(R.id.zapisDanOP);
        zapisDannueOP.setOnClickListener(v -> {
            v.startAnimation(animRotate);
            poleZapisStrelbu.append(new Date() + "\n" + pPozuvnoi.getText().toString()
                    + ",  Цель № " + pNZeli.getText().toString() + "\n");
            schetchikDX = 0;
            schetchikDY = 0;
            schetchikMinus = 0;
            schetchikPlus = 0;
            schetckikRazruvov = 0;
        });
        return view;
    }
}